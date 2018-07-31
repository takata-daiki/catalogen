package FastBE.invertedIndex;

import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;

public class margin {
	HashMap<String,HashMap<String,DSP_Info>>	dist;
	
	public margin(){
		dist = new HashMap<String,HashMap<String,DSP_Info>>();
	}
	
	public margin(margin M){
		if(dist != null)	dist.clear();
		dist = new HashMap<String,HashMap<String,DSP_Info>>();
		if( M != null){
			for( String key : M.keySet()){
				dist.put(key, new HashMap<String,DSP_Info>());
				HashMap<String,DSP_Info> value = M.get(key);
				HashMap<String,DSP_Info> newValue = dist.get(key);
				for( String key2 : value.keySet())
					newValue.put(key2, new DSP_Info(value.get(key2)));
			}
		}
	}
	
	public HashMap<String,DSP_Info> get(String key){
		if(dist == null ) return null;
		return dist.get(key);
	}
	
	public Set<String> keySet(){
		if(dist == null ) return null;
		return dist.keySet();
	}
	
	public void clear(){
		if( dist != null){
			for( String key : dist.keySet()){
				HashMap<String,DSP_Info> value = dist.get(key);
				for( String key2 : value.keySet())
					value.get(key2).clear();
				value.clear();
			}
			dist.clear();
		}
	}
	public margin(JSONObject jsonMargin_, config configObj) {
		if(dist != null)	dist.clear();
		dist = new HashMap<String,HashMap<String,DSP_Info>>();

		JSONObject jsonMargin = (JSONObject) jsonMargin_.get(configObj.getKeyword(config.MARGIN));
		if(jsonMargin != null)
			for(Object attri : jsonMargin.keySet()){
				JSONObject jsonAttri = (JSONObject) jsonMargin.get((String) attri);
				HashMap<String,DSP_Info> value = new HashMap<String,DSP_Info>();
				for(Object jsonValue : jsonAttri.keySet())
					value.put((String) jsonValue, new DSP_Info((JSONObject) jsonAttri.get((String) jsonValue)));
				
				dist.put((String) attri, value);
			}
	}
	public String toJSONString(){
		return toJSONObj().toJSONString();
	}

	/**
	 * @return JSONObject of the price instance.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObj(){
		JSONObject jsonObj = new JSONObject();
		for(String attri : dist.keySet()){
			JSONObject jsonAttri = new JSONObject();
			for(String value : dist.get(attri).keySet())
				jsonAttri.put(value, dist.get(attri).get(value).toJSONObj() );
			jsonObj.put(attri, jsonAttri);
		}
		return jsonObj;
	}

	public int size(){
		return dist.size();
	}
}
