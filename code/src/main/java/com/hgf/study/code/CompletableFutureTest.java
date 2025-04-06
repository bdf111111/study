package com.hgf.study.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) {
        List<CompletableFuture<Void>> tasks = new ArrayList<>();
        tasks.add(CompletableFuture.runAsync(() -> System.out.println(1/0))
                .handle((result, ex) -> {
                    if (Objects.nonNull(ex)) {
                        ex.printStackTrace();
                    }
                    return null;
                }));
        tasks.add(CompletableFuture.runAsync(() -> {
            Integer i = null;
            System.out.println(i.toString());
        }).handle((result, ex) -> {
            if (Objects.nonNull(ex)) {
                ex.printStackTrace();
            }
            return null;
        }));
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
    }

}
