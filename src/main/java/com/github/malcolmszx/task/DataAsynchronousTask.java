package com.github.malcolmszx.task;

import com.github.malcolmszx.bootstrap.data.InChatToDataBaseService;
import com.github.malcolmszx.common.utils.MessageChangeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by MySelf on 2018/12/3.
 */
public class DataAsynchronousTask {

    private final Logger log = LoggerFactory.getLogger(DataAsynchronousTask.class);

    private final InChatToDataBaseService inChatToDataBaseService;

    public DataAsynchronousTask(InChatToDataBaseService inChatToDataBaseService){
        this.inChatToDataBaseService = inChatToDataBaseService;
    }

    public void writeData(Map<String,Object> maps) throws Exception {
        log.info("【异步写入数据】");
        new Thread(new Runnable() {
            @Override
            public void run() {
                inChatToDataBaseService.writeMapToDB(MessageChangeUtil.Change(maps));
            }
        }).start();
    }

}
