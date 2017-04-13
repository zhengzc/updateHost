package com.zzc.updataHost;

import com.zzc.updataHost.core.RequestMethod;
import com.zzc.updataHost.core.SimpleHttpRequest;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created by ying on 2016/12/21.
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    private static final String URL = "https://raw.githubusercontent.com/racaljk/hosts/master/hosts";
    private static final String CUTTING_LING = "#===== update hosts =====";
    private static final String HOST_FILE_PATH_LINUX = "/etc/hosts";
    private static final String HOST_FILE_PATH_WIN = "C:\\Windows\\System32\\drivers\\etc\\hosts";

//    private static final String HOST_FILE_PATH = "/Users/ying/test/hosts.txt";

    public static void main(String[] args) throws Exception{
        System.out.println("==> start update hosts");

        String HOST_FILE_PATH = HOST_FILE_PATH_LINUX;
        String os = System.getProperty("os.name").toLowerCase();
        if(os.startsWith("win")){
            HOST_FILE_PATH = HOST_FILE_PATH_WIN;
        }


        SimpleHttpRequest simpleHttpRequest = new SimpleHttpRequest(URL, RequestMethod.GET);
        String content = simpleHttpRequest.call();

        List<String> hostConfigs = FileUtils.readLines(new File(HOST_FILE_PATH));

        int index = hostConfigs.indexOf(CUTTING_LING);
        if(index != -1) {
            hostConfigs = hostConfigs.subList(0, index);
        }

        hostConfigs.add(CUTTING_LING);
        hostConfigs.add(content);

        FileUtils.writeLines(new File(HOST_FILE_PATH),hostConfigs);

        System.out.println("===> complete update");
    }
}
