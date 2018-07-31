package skala.erp.bmp.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.util.Calendar;

import skala.erp.bmp.baseenums.ItemType;
import skala.erp.bmp.baseenums.NumberSeqList;
import skala.erp.bmp.baseenums.ProdStatus;
import skala.erp.bmp.baseenums.ProdType;
import skala.erp.bmp.server.NumberSeqHolder;
import skala.erp.bmp.shared.dto.ITableDTO;
import skala.erp.bmp.shared.dto.ProdTableDetails;
import skala.erp.bmp.shared.entity.InventTable;
import skala.erp.bmp.shared.entity.ProdTable;

@Repository
@Transactional
public class ProdTableDAO extends AbstractDAO<ProdTable> {

	InventDimDAO inventDimDAO;

	@Autowired
	InventTableDAO inventTableDAO;

	public ProdTableDAO() {
		super(ProdTable.class);
	}

	public InventDimDAO getInventDimDAO() {
		return inventDimDAO;
	}

	public void setInventDimDAO(InventDimDAO inventDimDAO) {
		this.inventDimDAO = inventDimDAO;
	}

	@Override
	public ProdTable find(String prodId, String dataAreaId) {

		ProdTable prodTable = null;

		Query query = this
				.getCurrentSession()
				.createQuery(
						"from ProdTable as prodTable "
								+ "WHERE prodTable.prodId = :prodId and prodTable.dataAreaId= :dataAreaId");

		prodTable = (ProdTable) query.setString("prodId", prodId)
				.setString("dataAreaId", dataAreaId).uniqueResult();

		return prodTable;
	}

	public double getQtyProducedTodayItem(String itemId, String dataAreaId) {
		double result = 0;
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(
				"SELECT sum(prodTable.Qty_St_Up) FROM Prod_Table as prodTable WHERE"
						+ " prodTable.BOM_Id = :itemId AND"
						+ " prodTable.Finished_Date = :date AND"
						+ " prodTable.Prod_Status = 3 AND"
						+ " prodTable.Data_Area_Id = :dataAreaId");
		try {
			result = (double) sqlQuery.setString("itemId", itemId)
					.setDate("date", new Date())
					.setString("dataAreaId", dataAreaId).uniqueResult();
		} catch (NullPointerException exception) {
			return 0;
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Double> getProducedQtyGroupByWeekInDateRangeInAllCompanies(
			String BOMId, Date fromDate, Date toDate) {
		Map<String, Double> res = new HashMap<String, Double>();
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(
				"SELECT prodTable.Finished_Date, sum(prodTable.Qty_St_Up)"
						+ " FROM Prod_Table as prodTable WHERE"
						+ " prodTable.Prod_Status = 3 AND"
						+ " prodTable.BOM_Id = :BOMId AND"
						+ " prodTable.Finished_Date >= :fromDate AND"
						+ " prodTable.Finished_Date <= :toDate"
						+ " GROUP BY prodTable.Finished_Date"
						+ " ORDER BY prodTable.Finished_Date ASC");

		Iterator iterator = sqlQuery.setString("BOMId", BOMId)
				.setDate("fromDate", fromDate).setDate("toDate", toDate).list()
				.iterator();
		Calendar calendar = Calendar.getInstance();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			Date date = (Date) objects[0];
			calendar.setTime(date);
			String key = String.format("%2d(%d)",
					calendar.get(Calendar.WEEK_OF_YEAR),
					calendar.get(Calendar.YEAR));

			Double value = 0.0;
			if (objects[1] != null) {
				value = (Double) objects[1];
			}
			if (res.containsKey(key))
				res.put(key, res.get(key) + value);
			else
				res.put(key, value);
		}
		Date tempDate = new Date(fromDate.getTime());
		calendar.setTime(tempDate);
		while (calendar.getTime().before(toDate)) {
			String key = String.format("%2d(%d)",
					calendar.get(Calendar.WEEK_OF_YEAR),
					calendar.get(Calendar.YEAR));
			if (!res.containsKey(key))
				res.put(key, 0.0);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Double> getProducedQtyGroupByWeekInDateRange(
			String BOMId, String dataAreaId, Date fromDate, Date toDate) {
		Map<String, Double> res = new HashMap<String, Double>();
		SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(
				"SELECT prodTable.Finished_Date, sum(prodTable.Qty_St_Up)"
						+ " FROM Prod_Table as prodTable WHERE"
						+ " prodTable.Prod_Status = 3 AND"
						+ " prodTable.BOM_Id = :BOMId AND"
						+ " prodTable.Data_Area_Id = :dataAreaId AND"
						+ " prodTable.Finished_Date >= :fromDate AND"
						+ " prodTable.Finished_Date <= :toDate"
						+ " GROUP BY prodTable.Finished_Date"
						+ " ORDER BY prodTable.Finished_Date ASC");

		Iterator iterator = sqlQuery.setString("BOMId", BOMId)
				.setString("dataAreaId", dataAreaId)
				.setDate("fromDate", fromDate).setDate("toDate", toDate).list()
				.iterator();
		Calendar calendar = Calendar.getInstance();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			Date date = (Date) objects[0];
			calendar.setTime(date);
			String key = String.format("%2d(%d)",
					calendar.get(Calendar.WEEK_OF_YEAR),
					calendar.get(Calendar.YEAR));

			Double value = 0.0;
			if (objects[1] != null) {
				value = (Double) objects[1];
			}
			if (res.containsKey(key))
				res.put(key, res.get(key) + value);
			else
				res.put(key, value);
		}
		Date tempDate = new Date(fromDate.getTime());
		calendar.setTime(tempDate);
		while (calendar.getTime().before(toDate)) {
			String key = String.format("%2d(%d)",
					calendar.get(Calendar.WEEK_OF_YEAR),
					calendar.get(Calendar.YEAR));
			if (!res.containsKey(key))
				res.put(key, 0.0);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, Double> getProducedQtyGroupByDay(String BOMId,
			String dataAreaId) {
		Map<Integer, Double> res = new HashMap<Integer, Double>();
		SQLQuery sqlQuery = this
				.getCurrentSession()
				.createSQLQuery(
						"SELECT WEEKOFYEAR(prodTable.Finished_Date) as period, sum(prodTable.Qty_St_Up)"
								+ " FROM Prod_Table as prodTable WHERE"
								+ " prodTable.Prod_Status = 3 AND"
								+ " prodTable.BOM_Id = :BOMId AND"
								+ " prodTable.Data_Area_Id = :dataAreaId"
								+ " GROUP BY period" + " ORDER BY period ASC");

		Iterator iterator = sqlQuery.setString("BOMId", BOMId)
				.setString("dataAreaId", dataAreaId).list().iterator();
		int currentWeekNumber = Calendar.getInstance().get(
				Calendar.WEEK_OF_YEAR);
		int startWeekNumber;
		if (currentWeekNumber <= 10) {
			startWeekNumber = 1;
		} else
			startWeekNumber = currentWeekNumber - 10;
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			int weekNum = (Integer) objects[0];
			if (weekNum < startWeekNumber
					|| (startWeekNumber == 1 && weekNum > currentWeekNumber)
					|| weekNum > currentWeekNumber)
				continue;
			if (objects[1] == null)
				res.put((Integer) objects[0], 0.0);
			else
				res.put((Integer) objects[0], (Double) objects[1]);
		}
		for (Integer i = startWeekNumber; i <= currentWeekNumber; i++)
			if (!res.containsKey(i))
				res.put(i, 0.0);
		return res;
	}

	@SuppressWarnings("rawtypes")
	public Map<Integer, Double> getProducedQtyGroupByDayForAllCompanies(
			String BOMId) {
		Map<Integer, Double> res = new HashMap<Integer, Double>();
		SQLQuery sqlQuery = this
				.getCurrentSession()
				.createSQLQuery(
						"SELECT WEEKOFYEAR(prodTable.Finished_Date) as period, sum(prodTable.Qty_St_Up)"
								+ " FROM Prod_Table as prodTable WHERE"
								+ " prodTable.Prod_Status = 3 AND"
								+ " prodTable.BOM_Id = :BOMId"
								+ " GROUP BY period" + " ORDER BY period ASC");
		Iterator iterator = sqlQuery.setString("BOMId", BOMId).list()
				.iterator();
		int currentWeekNumber = Calendar.getInstance().get(
				Calendar.WEEK_OF_YEAR);
		int startWeekNumber;
		if (currentWeekNumber <= 10) {
			startWeekNumber = 1;
		} else
			startWeekNumber = currentWeekNumber - 10;
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			int weekNum = (Integer) objects[0];
			if (weekNum < startWeekNumber
					|| (startWeekNumber == 1 && weekNum > currentWeekNumber)
					|| weekNum > currentWeekNumber) {
				continue;
			}

			if (objects[1] == null)
				res.put((Integer) objects[0], 0.0);
			else
				res.put((Integer) objects[0], (Double) objects[1]);
		}
		for (Integer i = startWeekNumber; i <= currentWeekNumber; i++)
			if (!res.containsKey(i))
				res.put(i, 0.0);
		return res;
	}

	public boolean existServiceProdOrder(String BOMId, String dataAreaId) {
		Query query = this.getCurrentSession().createQuery(
				"from ProdTable as prodTable WHERE"
						+ " prodTable.BOMId = :BOMId AND"
						+ " prodTable.finishedDate = :today AND"
						+ " prodTable.prodStatus = :status AND"
						+ " prodTable.dataAreaId = :dataAreaId");
		ProdTable table = (ProdTable) query.setString("BOMId", BOMId)
				.setDate("today", new Date())
				.setInteger("status", ProdStatus.Completed.getIntValue())
				.setString("dataAreaId", dataAreaId).uniqueResult();
		if (table == null)
			return false;
		else
			return true;
	}

	@SuppressWarnings("unchecked")
	public List<ProdTable> getAllCompletedProdOrdersInDateRange(Date fromDate,
			Date toDate, String dataAreaId) {
		List<ProdTable> list = new ArrayList<ProdTable>();
		Query query = this.getCurrentSession().createQuery(
				"from ProdTable as prodTable WHERE"
						+ " prodTable.stUpDate >= :fromDate AND"
						+ " prodTable.stUpDate <= :toDate AND"
						+ " prodTable.prodStatus = :status AND"
						+ " prodTable.dataAreaId = :dataAreaId");
		list = query.setString("dataAreaId", dataAreaId)
				.setInteger("status", ProdStatus.Completed.getIntValue())
				.setDate("fromDate", fromDate).setDate("toDate", toDate).list();
		return list;
	}

	@Override
	public ProdTableDetails createDTO(ProdTable prodTable) {

		if (prodTable == null)
			return new ProdTableDetails();
		return new ProdTableDetails(prodTable);
	}

	@Override
	public ProdTable createEntity(ITableDTO dtoObject, String dataAreaId) {

		ProdTable prodTable = null;
		ProdTableDetails prodTableDetails = (ProdTableDetails) dtoObject;

		if (prodTableDetails == null)
			return prodTable;

		if (prodTableDetails.getRecId() != 0) {

			prodTable = this.findRecId(prodTableDetails.getRecId());
		}

		if (prodTable == null) {

			prodTable = new ProdTable();

			prodTable.setProdId(NumberSeqHolder.getInstance().getNumberSeq()
					.newGetNum(NumberSeqList.ProdId, dataAreaId));
			prodTable.setDataAreaId(dataAreaId);
			prodTable.setRecId(0);
		}
		prodTable.setProdStatus(prodTableDetails.getProdStatus());
		prodTable.setBOMId(prodTableDetails.getBOMId());
		InventTable item = inventTableDAO.find(prodTableDetails.getBOMId(),
				dataAreaId);
		if (item.getItemType() == ItemType.Packed.getIntValue()) {
			prodTable.setProdType(ProdType.Packing.getIntValue());
		} else {
			prodTable.setProdType(ProdType.Production.getIntValue());
		}
		prodTable.setInventDimId(inventDimDAO.findOrCreate(
				prodTableDetails.getInventDim().getInventLocationId(),
				dataAreaId).getInventDimId());
		prodTable.setQtyStUp(prodTableDetails.getStUpQty());
		prodTable.setStUpDate(prodTableDetails.getStUpDate());
		prodTable.setFinishedDate(prodTableDetails.getFinishedDate());

		prodTable.setRecVersion(prodTableDetails.getRecVersion());
		return prodTable;
	}

}
