package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author ld
 * @date 2021/9/16 22:04
 */
public class PrintProcessor extends Thread implements IRequestProcessor{

    protected IRequestProcessor nextProcessor;

    protected BlockingQueue<Request> requests = new LinkedBlockingDeque<>();

    public PrintProcessor(IRequestProcessor nextProcessor){
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void processRequest(Request request) {
        // doSomething
        requests.add(request);
    }

    @Override
    public void run() {
        while (true) {
            try{
                // 异步进行请求处理
                Request request = requests.take();
                System.out.println("PrintProcessor: "+request);
                if (null != nextProcessor) {
                    nextProcessor.processRequest(request);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
