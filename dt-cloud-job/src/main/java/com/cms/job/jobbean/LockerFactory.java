package com.cms.job.jobbean;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ydf Created by 2021/12/10 15:41
 */
public class LockerFactory {

    private static Map<String,Locker> lockerMap = new ConcurrentHashMap<>();

    private static Map<String,ReadWriteLocker> readWriteLockerMap = new ConcurrentHashMap<>();

    public synchronized static Locker getLocker(String key){
        Locker counter = lockerMap.get(key);
        if(counter == null) {
            counter = new Locker();
            lockerMap.put(key,counter);
        }
        return counter;
    }

    public static class Locker implements Serializable {

        private Lock lock = new ReentrantLock();

        public Locker() {
        }

        public boolean tryLock(){
            return lock.tryLock();
        }

        public boolean tryLock(long timeout){
            try {
                return lock.tryLock(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
            return false;
        }

        public void unlock(){
            lock.unlock();
        }

        public void lock(){
            lock.lock();
        }
    }

    public synchronized static ReadWriteLocker getReadWriteLocker(String key) {
        ReadWriteLocker counter=readWriteLockerMap.get(key);
        if(counter==null){
            counter=new ReadWriteLocker();
            readWriteLockerMap.put(key,counter);
        }
        return counter;
    }

    public static class ReadWriteLocker implements Serializable {
        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = readWriteLock.readLock();
        private Lock writeLock = readWriteLock.writeLock();

        public void readLock(){
            readLock.lock();
        }

        public boolean readTryLock(){
            return readLock.tryLock();
        }

        public boolean readTryLock(long timeout) {
            try {
                return readLock.tryLock(timeout,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        public void readUnLock(){
            readLock.unlock();
        }

        public void writeLock(){
            writeLock.lock();
        }

        public boolean writeTryLock(){
            return writeLock.tryLock();
        }

        public boolean writeTryLock(long timeout){
            try {
                return writeLock.tryLock(timeout,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        public void writeUnLock(){
            writeLock.unlock();
        }
    }


}
