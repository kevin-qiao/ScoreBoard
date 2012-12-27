package com.qiao.scoreboard.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {

    public static String inputStreamToString(final InputStream stream, int type) 
    		throws IOException {
    	
    	BufferedReader br = null;
    	
    	if(type == 0){
    		br = new BufferedReader(new InputStreamReader(stream, "GB2312"));
    	}else{
    		br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
    	}
    	
        
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }

}
