package net.wisedream.common.tm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pseudo on 4/20/15.
 */
public class TaskContext{
    Logger logger;
    private static ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new ConcurrentHashMap<String, Object>();
        }
    };

    public TaskContext(Logger logger) {
        this.logger = logger;
    }

    public TaskContext() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public <T> T getContextualObject(String key) {
        return (T) CONTEXT.get().get(key);
    }

    public void putContextualObject(String key, Object info) {
        CONTEXT.get().put(key, info);
    }

    public void d(String msg){
        logger.debug(msg);
    }

    public void i(String msg) {
        logger.info(msg);
    }

    public void w(String msg){
        logger.warn(msg);
    }

    public void e(String msg) {
        logger.error(msg);
    }
}
