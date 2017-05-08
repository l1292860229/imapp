package com.coolwin.entity.appentity;

/**
 * Created by dell on 2017/5/6.
 */
public class AppResult {
    private Object data;
    private State state;
    private String max;
    private String min;
    private String speakStatus;

    public void setStateValue(int code,String msg,String debugMsg,String url){
        if (state==null) {
            state = new State();
        }
        state.setCode(code);
        state.setMsg(msg);
        state.setDebugMsg(debugMsg);
        state.setUrl(url);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getSpeakStatus() {
        return speakStatus;
    }

    public void setSpeakStatus(String speakStatus) {
        this.speakStatus = speakStatus;
    }

    public class State{
        private int code;
        private String msg;
        private String debugMsg;
        private String url;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getDebugMsg() {
            return debugMsg;
        }

        public void setDebugMsg(String debugMsg) {
            this.debugMsg = debugMsg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
