package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;

import com.atlassian.jira.rest.client.domain.Issue;

import constants.Impact;
import constants.ViewConstants;

public final class SiteUtils {

	static final SimpleDateFormat jiraDateFormat = new SimpleDateFormat(ViewConstants.JIRA_DATE_FORMAT);

	static Date today = null;

	public SiteUtils() {

	}

	public static void setTodaysDate() {
		try {
			today = jiraDateFormat.parse(jiraDateFormat.format(new Date()));
		} catch (ParseException e) {
			today = new Date();
		}
	}

	public static String dateToString(DateTime date) {

		if (date != null) {
			return date.toString(ViewConstants.FORMATO_DATA);
		}

		return ViewConstants.NAO_DISPONIVEL;
	}

	public static Impact gerarDefeito(Issue bug, Integer qtdeCTs) {
		Impact impact = new Impact();

		String processo = null;
		String responsavel = null;

		try {
			processo = ((JSONObject) bug.getField(ViewConstants.PROCESS_FIELD).getValue()).get(ViewConstants.VALUE)
					.toString();
			impact.setProcesso(returnResumedProccess(processo));
		} catch (Exception e) {
			impact.setProcesso(ViewConstants.NAO_DISPONIVEL);
		}

		StringBuilder resumo = new StringBuilder();
		resumo.append(bug.getKey());
		resumo.append(ViewConstants.ESPACO_TRACO_ESPACO);
		resumo.append(bug.getSummary());

		impact.setResumo(resumo.toString());

		impact.setQtdeCTs(qtdeCTs);

		StringBuilder assignee = new StringBuilder();
		assignee.append(bug.getAssignee().getDisplayName());
		assignee.append(ViewConstants.ESPACO_BARRA_ESPACO);
		try {
			responsavel = ((JSONObject) bug.getField(ViewConstants.TEAM_FIELD).getValue()).get(ViewConstants.VALUE)
					.toString();
			assignee.append(responsavel);
		} catch (JSONException e) {
			assignee.append(ViewConstants.NAO_DISPONIVEL);
		}

		impact.setResponsavel(assignee.toString());

		impact.setStatus(bug.getStatus().getName());
		impact.setDataCriacao(SiteUtils.dateToString(bug.getCreationDate()));
		impact.setDataResolucao(SiteUtils.dateToString(bug.getDueDate()));

		return impact;
	}

	public static Impact gerarImpactoGenerico(Issue bug, String processo, Integer qtdeCTs) {
		Impact impact = new Impact();

		String responsavel = null;

		impact.setProcesso(processo);

		StringBuilder resumo = new StringBuilder();
		resumo.append(bug.getKey());
		resumo.append(ViewConstants.ESPACO_TRACO_ESPACO);
		resumo.append(bug.getSummary());

		impact.setResumo(resumo.toString());

		impact.setQtdeCTs(qtdeCTs);

		StringBuilder assignee = new StringBuilder();
		assignee.append(bug.getAssignee().getDisplayName());
		assignee.append(ViewConstants.ESPACO_BARRA_ESPACO);
		try {
			responsavel = ((JSONObject) bug.getField(ViewConstants.TEAM_FIELD).getValue()).get(ViewConstants.VALUE)
					.toString();
			assignee.append(responsavel);
		} catch (Exception e) {
			assignee.append(ViewConstants.NAO_DISPONIVEL);
		}

		impact.setResponsavel(assignee.toString());

		impact.setStatus(bug.getStatus().getName());
		impact.setDataCriacao(SiteUtils.dateToString(bug.getCreationDate()));
		impact.setDataResolucao(SiteUtils.dateToString(bug.getDueDate()));

		return impact;
	}

	public static String returnResumedProccess(String str) {

		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_1)) {
			return ViewConstants.RESUMIDO_1;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_2)) {
			return ViewConstants.RESUMIDO_2;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_3)) {
			return ViewConstants.RESUMIDO_3;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_4)) {
			return ViewConstants.RESUMIDO_4;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_5)) {
			return ViewConstants.RESUMIDO_5;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_6)) {
			return ViewConstants.RESUMIDO_6;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_7)) {
			return ViewConstants.RESUMIDO_7;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_8)) {
			return ViewConstants.RESUMIDO_8;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_9)) {
			return ViewConstants.RESUMIDO_9;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_10)) {
			return ViewConstants.RESUMIDO_10;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_11)) {
			return ViewConstants.RESUMIDO_11;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_12)) {
			return ViewConstants.RESUMIDO_12;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_13)) {
			return ViewConstants.RESUMIDO_13;
		}
		if (str.equalsIgnoreCase(ViewConstants.EXTENSO_14)) {
			return ViewConstants.RESUMIDO_14;
		}

		return ViewConstants.NAO_DISPONIVEL;
	}

	public static void generateReport(List<Impact> listaImpactos) throws IOException {

		File controleMonet = new File("Controle Monet.xlsx");

		FileInputStream inputStream = new FileInputStream(controleMonet);

		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		XSSFSheet sheet = workbook.getSheet("Impactos");

		int rownum = 1;
		int cellnum = 0;

		XSSFRow row = null;

		Impact item = null;

		for (int i = 0; i < listaImpactos.size(); i++) {

			item = listaImpactos.get(i);

			row = sheet.createRow(rownum);

			row.createCell(cellnum).setCellValue(item.getProcesso());
			cellnum++;
			row.createCell(cellnum).setCellValue(item.getResumo());
			cellnum++;
			row.createCell(cellnum).setCellValue(item.getQtdeCTs());
			cellnum++;
			row.createCell(cellnum).setCellValue(item.getResponsavel());
			cellnum++;
			row.createCell(cellnum).setCellValue(item.getDataCriacao());
			cellnum++;
			row.createCell(cellnum).setCellValue(item.getDataResolucao());

			rownum++;
			cellnum = 0;
		}

		inputStream.close();

		FileOutputStream outputStream = new FileOutputStream(controleMonet);

		workbook.write(outputStream);

		workbook.close();

		outputStream.close();

	}

	public static void popularListaStatus(HashMap<String, Integer> qtdeStatus) {

		qtdeStatus.put("Passou", 0);
		qtdeStatus.put("Falha", 0);
		qtdeStatus.put("Massa", 0);
		qtdeStatus.put("FalhaMassa", 0);
		qtdeStatus.put("Bloqueados", 0);
		qtdeStatus.put("BloqueadosExec", 0);
		qtdeStatus.put("NaoExec", 0);
		qtdeStatus.put("Reexecutar", 0);
		qtdeStatus.put("ForaEscopo", 0);
		qtdeStatus.put("ExecHoje", 0);
		qtdeStatus.put("ExecHojeOk", 0);

	}

	public static Integer validaExecutadoHoje(String data) {

		Date date = null;

		try {

			date = jiraDateFormat.parse(data);

			return today.compareTo(date);

		} catch (ParseException e) {
			return -1;
		}

	}

	public static void atualizarContagemCts(String status, Integer qtdExec, String data,
			HashMap<String, Integer> qtdeStatus) {

		// TO DO atualizar logica para considerar qtde e data de execucao

		int qtde = 0;

		if (status.equalsIgnoreCase("passed")) {

			qtde = qtdeStatus.get("Passou") + 1;

			qtdeStatus.put("Passou", qtde);

		}

		if (status.equalsIgnoreCase("failed")) {

			qtde = qtdeStatus.get("Falha") + 1;

			qtdeStatus.put("Falha", qtde);

		}

		if (status.equalsIgnoreCase("awaiting data")) {

			qtde = qtdeStatus.get("Massa") + 1;

			qtdeStatus.put("Massa", qtde);

		}

		if (status.equalsIgnoreCase("blocked")) {

			qtde = qtdeStatus.get("Bloqueados") + 1;

			qtdeStatus.put("Bloqueados", qtde);

		}

		if (status.equalsIgnoreCase("no run") || status.equalsIgnoreCase("running")) {

			qtde = qtdeStatus.get("NaoExec") + 1;

			qtdeStatus.put("NaoExec", qtde);

		}

		if (status.equalsIgnoreCase("out of scope")) {

			qtde = qtdeStatus.get("ForaEscopo") + 1;

			qtdeStatus.put("ForaEscopo", qtde);

		}

	}

}
