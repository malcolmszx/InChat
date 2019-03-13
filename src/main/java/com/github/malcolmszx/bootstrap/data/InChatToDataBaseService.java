package com.github.malcolmszx.bootstrap.data;

import com.github.malcolmszx.common.bean.InChatMessage;

/**
 * Created by MySelf on 2018/12/3.
 */
public interface InChatToDataBaseService {

    Boolean writeMapToDB(InChatMessage message);

}
