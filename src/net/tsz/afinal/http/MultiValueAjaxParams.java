package net.tsz.afinal.http;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
/**
 * 支持数组参数的ajaxParams
 */
public class MultiValueAjaxParams extends AjaxParams{

	protected ConcurrentHashMap<String, Object> urlParams = new ConcurrentHashMap<String, Object>();
	@Override
	public void put(String key, String value) {
		Object val = urlParams.get(key);
		if(val != null){
			if(val instanceof List){
				((List<String>)val).add(value);
			}else{
				List<String> list=new LinkedList<String>();
				list.add((String) val);
				list.add(value);
				urlParams.put(key,list);
			}
		}else {
			urlParams.put(key, value);
		}
	}

	public void put(String key,List<String> values){
		for(String s:values){
			put(key,s);
		}
	}

	@Override
	protected List<BasicNameValuePair> getParamsList() {
		List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();

		for(ConcurrentHashMap.Entry<String, Object> entry : urlParams.entrySet()) {
			Object val=entry.getValue();
			if(val instanceof List){
				for(String s:(List<String>)val){
					lparams.add(new BasicNameValuePair(entry.getKey(), s));
				}
			}else {
				lparams.add(new BasicNameValuePair(entry.getKey(), (String) val));
			}
		}

		return lparams;
	}
}