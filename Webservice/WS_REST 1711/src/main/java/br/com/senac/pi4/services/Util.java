package br.com.senac.pi4.services;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Rafael
 */
class Util {
    public static boolean isNotNull(String texto) {
        return texto != null && texto.trim().length() >= 0;
    }
    
    public static String constructJSON(String tag, boolean status) {
        JSONObject obj = new JSONObject();
        
        try {
            obj.put("tag", tag);
            obj.put("status", status);
        } catch (JSONException e) {
            return("Erro na criação do JSON:" + e);
        }
        return obj.toString();
    }
    
    public static String constructJSON(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        
        try {
            obj.put("tag", tag);
            obj.put("status", status);
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            return("Erro na criação do JSON:" + e);
        }
        return obj.toString(); 
    }
}