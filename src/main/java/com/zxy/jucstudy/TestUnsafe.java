package com.zxy.jucstudy;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe学习
 */
public class TestUnsafe {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true); // theUnsafe是私有的，因此要设置为true
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(theUnsafe);

        // 获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        // 执行cas操作
        unsafe.compareAndSwapInt(teacher, idOffset, 0, 1);
        unsafe.compareAndSwapObject(teacher, nameOffset, null, "张三");

        // 验证
        System.out.println(teacher);
    }
}

@Data
class Teacher {
    volatile int id;
    volatile String name;
}
