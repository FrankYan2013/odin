package com.huifu.odin.integration.anc;

/**
 * @author frank
 */
public interface AncServiceClient {


    /**
     * 稽核入金接口
     *
     * @param message
     * @return NotifySendDTO
     */
    void sendToDingDing(String message);
}
