package br.com.unifacs.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import br.com.unifacs.dao.CustomQueryDao;
import br.com.unifacs.dao.DaoException;
import br.com.unifacs.utils.HttpJSFUtil;
import br.com.unifacs.utils.RdlUtils;

@ManagedBean(name="graficoMb")
@ViewScoped
public class GraficoDespesaReceitaMb implements Serializable {

	private CartesianChartModel categoryModel;
	private int ano;
	
	public GraficoDespesaReceitaMb() {
		this.ano= Calendar.getInstance().get(Calendar.YEAR);
		criarGrafico(null);
	}
	
	public void criarGrafico(ActionEvent event){
		
		categoryModel = new CartesianChartModel();
		if(RdlUtils.getProjetoAtual() == null){
			FacesContext.getCurrentInstance().addMessage("Atenзгo", new FacesMessage(FacesMessage.SEVERITY_WARN,"Selecione um projeto", null));
			return;
		}			
	
		
		ChartSeries receita = new ChartSeries();
		receita.setLabel("Receitas");
		ChartSeries despesa = new ChartSeries();
		despesa.setLabel("Despesas");
		
		Object[] receitas = null;
		Object[] despesas = null;
		
		try {
			receitas = CustomQueryDao.getTotalReceitaAnual(this.ano);
		} catch (DaoException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			despesas = CustomQueryDao.getTotalDespesaAnual(this.ano);
		} catch (DaoException e) {
			e.printStackTrace();
			return;
		}		
		
		receita.set("jan", (Number) receitas[0]);
		receita.set("fev", (Number) receitas[1]);
		receita.set("mar", (Number) receitas[2]);
		receita.set("abr", (Number) receitas[3]);
		receita.set("mai", (Number) receitas[4]);
		receita.set("jun", (Number) receitas[5]);
		receita.set("jul", (Number) receitas[6]);
		receita.set("ago", (Number) receitas[7]);
		receita.set("set", (Number) receitas[8]);
		receita.set("out", (Number) receitas[9]);
		receita.set("nov", (Number) receitas[10]);
		receita.set("dez", (Number) receitas[11]);
		
		despesa.set("jan", (Number) despesas[0]);
		despesa.set("fev", (Number) despesas[1]);
		despesa.set("mar", (Number) despesas[2]);
		despesa.set("abr", (Number) despesas[3]);
		despesa.set("mai", (Number) despesas[4]);
		despesa.set("jun", (Number) despesas[5]);
		despesa.set("jul", (Number) despesas[6]);
		despesa.set("ago", (Number) despesas[7]);
		despesa.set("set", (Number) despesas[8]);
		despesa.set("out", (Number) despesas[9]);
		despesa.set("nov", (Number) despesas[10]);
		despesa.set("dez", (Number) despesas[11]);
		
		categoryModel.addSeries(receita);
		categoryModel.addSeries(despesa);	
			
	}
	
	public void exportarExcel(ActionEvent event){
		
		Object[] receitas = null;
		Object[] despesas = null;
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("plan1");
			
			String meses[] ={"JAN","FEV","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
			HSSFRow linha1 = worksheet.createRow(0);
			
			linha1.createCell(0).setCellValue(new HSSFRichTextString("Mкs"));
			
			for(int i = 0; i < meses.length; i++){
				HSSFCell cell = linha1.createCell(i + 1);
				cell.setCellValue(new HSSFRichTextString(meses[i] + "/" + this.ano));
			}
			
			try {
				receitas = CustomQueryDao.getTotalReceitaAnual(this.ano);
			} catch (DaoException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro na exportaзгo", null));
				return;
			}
			
			HSSFRow linha2 = worksheet.createRow(1);
			linha2.createCell(0).setCellValue(new HSSFRichTextString("Receitas"));
			
			for(int i =0; i<receitas.length; i++ ){
				HSSFCell cell = linha2.createCell(i + 1);
				cell.setCellValue(new HSSFRichTextString(receitas[i].toString()));
			}
			
			try {
				despesas = CustomQueryDao.getTotalDespesaAnual(this.ano);
			} catch (DaoException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro na exportaзгo", null));				
				return;
			}			
			
			HSSFRow linha3 = worksheet.createRow(2);
			linha3.createCell(0).setCellValue(new HSSFRichTextString("Despesas"));
			
			for(int i =0; i<receitas.length; i++ ){
				HSSFCell cell = linha3.createCell(i + 1);
				cell.setCellValue(new HSSFRichTextString(despesas[i].toString()));
			}
			
			HttpServletResponse res = HttpJSFUtil.getResponse();   
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-disposition",  "attachment; filename=test.xls");
			ServletOutputStream out = res.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			FacesContext faces = FacesContext.getCurrentInstance();
			faces.responseComplete(); 
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro na exportaзгo", null));
			e.printStackTrace();
		}
		
	}
	
	
	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}
	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
	

}
