package bz.ziro.kanbe.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bz.ziro.kanbe.dao.SiteListDataDao;
import bz.ziro.kanbe.logic.ListLogic;
import bz.ziro.kanbe.model.SiteListData;

/**
 * ??????
 * @author Administrator
 *
 */
public class ListData {

    /**
     * ??????????
     * @param aKey
     * @return
     */
    public List<Map<String,String>> get(String aKey,int aNum) {

    	List<Map<String,String>> rtnList = new ArrayList<Map<String,String>>();
        //??????
        List<SiteListData> dataList = SiteListDataDao.findLastPageList(Long.valueOf(aKey),aNum);
        //?????????
        for ( SiteListData data : dataList ) { 
        	//XML???
        	Map<String,String> listData = ListLogic.createXMLMap(data.getData());
        	//??????
        	rtnList.add(listData);
        }
    	return rtnList;
    }

}
