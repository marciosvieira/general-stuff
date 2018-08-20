package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import org.codehaus.jettison.json.JSONObject;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.JiraRestClientFactory;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.IssueLink;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import constants.Impact;
import constants.ViewConstants;
import utils.SiteUtils;

public class Searcher {
	
	public static void main(String[] args) {
		
		Properties prop = new Properties();
		InputStream propInput = null;
		
		String url = null;
		String user = null;
		String pswd = null;
		
		String flag = null;
		Boolean showPopup = Boolean.FALSE;
		
		StringBuilder filter = null;
		
		try {
			
			propInput = new FileInputStream("config.properties");
			
			prop.load(propInput);
			
			url = prop.getProperty(ViewConstants.JIRA_URL);
			user = prop.getProperty(ViewConstants.JIRA_USERNAME);
			pswd = prop.getProperty(ViewConstants.JIRA_PASSWORD);
			
			flag = prop.getProperty(ViewConstants.FLAG);
			
			if (flag.equalsIgnoreCase(Boolean.TRUE.toString())) {
				showPopup = Boolean.TRUE;
			}
			
			filter = new StringBuilder();
			filter.append(ViewConstants.FILTER);
			filter.append(ViewConstants.SINAL_IGUAL);
			filter.append(prop.getProperty(ViewConstants.FILTER));
			
			HashMap<String, Integer> ctList = new HashMap<String, Integer>();
			Integer qtde = 0;
			
	        // Construct the JRJC client
	        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
	        URI uri = new URI(url);
	        JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, user, pswd);

	        Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql(filter.toString());
	        
//	        System.out.println("Total de CTs Bloqueados/Falhados: " + searchJqlPromise.claim().getTotal());
	        
	        if (showPopup) {
	        	JOptionPane.showMessageDialog(null, "Processando cts...");
			} else {
				System.out.println("Processando cts...");
			}
	        
	        
	        Issue item = null;
	        
	        Issue bug = null;
	        
	        for (BasicIssue issue : searchJqlPromise.claim().getIssues()) {
	            
//	        	System.out.print(".");
	        	
	        	item = client.getIssueClient().getIssue(issue.getKey()).get();
	        	
	        	for (IssueLink link : item.getIssueLinks()) {
	        		
//	        		System.out.println(link.getTargetIssueKey());

	        		if (ctList.containsKey(link.getTargetIssueKey())) {
	        			
	        			qtde = ctList.get(link.getTargetIssueKey()) + 1;
	        			
	        			ctList.put(link.getTargetIssueKey(), qtde);
	        			
	        		} else {
	        			ctList.put(link.getTargetIssueKey(), 1);
	        		}
					
				}
	            
	        }
	        
//	        System.out.println();
	        
	        List<Impact> listaImpactos = new ArrayList<Impact>();
	        
	        if (showPopup) {
	        	JOptionPane.showMessageDialog(null, "Processando impactos...");
	        } else {
	        	System.out.println("Processando impactos...");
	        }
	        
	        
	        String processo = null;
	        
	        for (Entry<String, Integer> bugId : ctList.entrySet()) {
	        	
	        	bug = client.getIssueClient().getIssue(bugId.getKey()).get();
	        	
	        	if (bug.getIssueType().getName().equalsIgnoreCase(ViewConstants.BUG) && 
	        			!bug.getStatus().getName().equalsIgnoreCase(ViewConstants.CLOSED)) {
	        		
	        		listaImpactos.add(SiteUtils.gerarDefeito(bug, bugId.getValue()));
	        		
//	        		System.out.print(".");
					
				}
	        	
	        	if (!bug.getIssueType().getName().equalsIgnoreCase(ViewConstants.BUG) &&
	        			!bug.getIssueType().getName().equalsIgnoreCase(ViewConstants.TEST_CASE) &&
	        			!bug.getStatus().getName().equalsIgnoreCase(ViewConstants.DONE)) {
	        		
	        		try {
	        			
	        			processo = SiteUtils.returnResumedProccess((((JSONObject) item.getField(
	        					ViewConstants.PROCESS_FIELD).getValue()).get(ViewConstants.VALUE).toString()));
						
					} catch (Exception e) {
						processo = ViewConstants.NAO_DISPONIVEL;
					}
	        		
	        		listaImpactos.add(SiteUtils.gerarImpactoGenerico(bug, processo, bugId.getValue()));
	        		
//	        		System.out.print(".");
					
				}

	        }
	        
//	        System.out.println();
	        
//	        System.out.println("Total de impactos: " + listaImpactos.size());
	        
	        SiteUtils.generateReport(listaImpactos);
	        
	        if (showPopup) {
	        	JOptionPane.showMessageDialog(null, "Terminado!");
	        } else {
	        	System.out.println("Terminado!");
	        }
	        
	        System.exit(0);
			
		} catch (FileNotFoundException e) {
			
			if (showPopup) {
				JOptionPane.showMessageDialog(null, "Erro localizando arquivo de configuração.");
	        } else {
	        	System.out.println("Erro localizando arquivo .");
	        }
			
		} catch (IOException e) {
			
			if (showPopup) {
				JOptionPane.showMessageDialog(null, "Erro no arquivo de configuração.");
	        } else {
	        	System.out.println("Erro no arquivo.");
	        }
			
		} catch (URISyntaxException e) {
			
			if (showPopup) {
				JOptionPane.showMessageDialog(null, "URL não encontrada.");
	        } else {
	        	System.out.println("URL não encontrada.");
	        }
			
		} catch (InterruptedException e) {
			
			if (showPopup) {
				JOptionPane.showMessageDialog(null, "Execução da query interrompida.");
	        } else {
	        	System.out.println("Execução da query interrompida.");
	        }
			
		} catch (ExecutionException e) {
			
			if (showPopup) {
				JOptionPane.showMessageDialog(null, "Erro na execução da query.");
	        } else {
	        	System.out.println("Erro na execução da query.");
	        }
			
		} catch (Exception e) {
			
			if (showPopup) {
				JOptionPane.showMessageDialog(null, "Exceção não mapeada!");
	        } else {
	        	System.out.println("Exceção não mapeada!");
	        	e.printStackTrace();
	        }
			
		} finally {
			System.exit(0);
		}
		
	}

}