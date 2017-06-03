package com.mycompany.inviter.flattener;

import java.util.ArrayList;
import java.util.List;


public class ArrayFlattener {

    public static List<Integer> flatten(Object [] array){
        if(array==null){
            throw new IllegalArgumentException("Array cannot be null or contain null elements");
        }

        List<Integer> flattenArray = new ArrayList<Integer>();

        for(Object element : array){
            if(element instanceof Integer){
                flattenArray.add((Integer)element);
            } else {
                if(element instanceof Object []){
                    flattenArray.addAll(flatten((Object [])element));
                } else {
                    throw new IllegalArgumentException("Array must only contain Integer elements");
                }
            }
        }

        return flattenArray;
    }


}
