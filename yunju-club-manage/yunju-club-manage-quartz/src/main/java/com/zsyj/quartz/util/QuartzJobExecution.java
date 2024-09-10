package com.zsyj.quartz.util;

import org.quartz.JobExecutionContext;
import com.zsyj.quartz.domain.SysJob;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author Xinxuan Zhuo
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
