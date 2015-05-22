package net.wisedream.common.tm;

/**
 * Created by zxp on 3/23/15.
 */
public interface Task {
    public Task perform(TaskContext context) throws Exception;
}
