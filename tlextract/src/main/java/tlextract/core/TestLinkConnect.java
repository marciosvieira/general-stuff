package tlextract.core;

import java.net.MalformedURLException;
import java.net.URL;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestLinkConnect {

	static final String testLinkKey = "6a120e048583bd95921ebe0db3c6544e";

	static final String testLinkUrl = "https://steps.everis.com/testlink/lib/api/xmlrpc/v1/xmlrpc.php";

	static final Integer projectID = 6289825;
	
	static final String projectName = "PROMONET - Projeto Monet";
	
	static final Integer planID = 6308290;
	
	static final String planName = "Projeto Monet - GPA - EXT-034402-00001";

	public static void main(String[] args) {

		try {
			execute();
		} catch (TestLinkAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void execute() throws TestLinkAPIException, MalformedURLException {

		TestLinkAPI api = new TestLinkAPI(new URL(testLinkUrl), testLinkKey);

//		TestPlan[] testPlan = api.getProjectTestPlans(6289825);
//		
//		for (int i = 0; i < testPlan.length; i++) {
//			System.out.println(testPlan[i].getId() + " - " + testPlan[i].getName());
//		}
		
//		6403507 - BRD's - Ciclo 1
//		6462859 - BRD's - Ciclo 1 Alternativos
//		6308290 - Projeto Monet - GPA - EXT-034402-00001
		
//		System.out.println(api.checkDevKey(testLinkKey));
		
//		Build[] builds = api.getBuildsForTestPlan(6403507);
//		
//		for (Build build : builds) {
//			System.out.println(build.getId() + " - " + build.getName() + " - " + build.getTestPlanId());
//		}
		
//		30790 - BRD's - Ciclo 1 
//		31017 - BRD's - Ciclo 1 Alternativos
//		31321 - BRD's - Ciclo 2 -  Alternativos Homologação
//		31316 - BRD's - Ciclo 2 - Homologação
//		31094 - Projeto Monet - GPA - EXT-034402-00001
		
		TestSuite[] suite = api.getTestSuitesForTestPlan(6403507);
		
		for (int i = 0; i < suite.length; i++) {
			System.out.println(suite[i].getId() + " - " + suite[i].getName());
		}
		
//		TAVA AQUI
//		api.getLastExecutionResult(6403507, testCaseId, testCaseExternalId)
		
//		TestCase[] tests = api.getTestCasesForTestSuite(6308291, true, TestCaseDetails.FULL);
//		
//		for (int i = 0; i < tests.length; i++) {
//			System.out.println(tests[i].getFullExternalId() + " - " + tests[i].getName() + " - " + tests[i].getExecutionStatus());
//		}
		

	}

}