package com.mycompany.inviter.flattener;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;


public class ArrayFlattenerTest{

    @Test
    public void whenArrayIsNullShouldThrowException() {
        Object [] array = null;

        try {
            ArrayFlattener.flatten(array);
        } catch (IllegalArgumentException e){
            assertNotNull(e);
        }
    }


    @Test
    public void whenArrayIsEmptyReturnEmptyList() {
        Object [] array = {};

        List<Integer> flattenList = ArrayFlattener.flatten(array);

        assertTrue(flattenList.isEmpty());
    }


    @Test
         public void whenArrayHasOneIntegerReturnListWithThatObject() {
        Object [] array = {4};

        List<Integer> flattenList = ArrayFlattener.flatten(array);

        assertTrue(flattenList.size() == 1);
        assertThat(flattenList, hasItem(4));
    }


    @Test
    public void whenArrayHasOnlyOneInnerArrayShouldReturnAllElementsFromThatArray() {
        Object [][] array = {{1,2,3}};

        List<Integer> flattenList = ArrayFlattener.flatten(array);

        assertTrue(flattenList.size() == 3);
        assertThat(flattenList, hasItems(1, 2, 3));
        assertArrayEquals(new Integer[]{1, 2, 3}, flattenList.toArray());
    }


    @Test
    public void whenArrayHasOneIntegerAndOneArrayShouldReturnAllElementsInOrder() {
        Object [] array = new Object[2];
        Object [] innerArray = {2,4,6};

        array[0]=5;
        array[1]= innerArray;

        List<Integer> flattenList = ArrayFlattener.flatten(array);

        assertTrue(flattenList.size()==4);
        assertThat(flattenList, hasItems(2, 4, 6));
        assertArrayEquals(new Integer[]{5, 2, 4, 6}, flattenList.toArray());
    }


    @Test
    public void whenArrayHasMultipleInnerArraysAndIntegersArraysShouldReturnAllElementsInOrder() {
        Object [] mainArray = new Object[3];
        Object [] firstLevelArray = new Object[3];
        Object [] secondLevelArray1 = new Object[3];
        Object [] secondLevelArray2 = new Object[3];

        secondLevelArray1[0]=6;
        secondLevelArray1[1]=7;
        secondLevelArray1[2]=8;

        secondLevelArray2[0]=2;
        secondLevelArray2[1]=4;
        secondLevelArray2[2]=3;

        firstLevelArray[0] = secondLevelArray1;
        firstLevelArray[1] = secondLevelArray2;
        firstLevelArray[2] = 23;

        mainArray[0]=5;
        mainArray[1]= firstLevelArray;
        mainArray[2]= 78;

        List<Integer> flattenList = ArrayFlattener.flatten(mainArray);

        assertTrue(flattenList.size()==9);
        assertThat(flattenList, hasItems(6,7,8,2,4,3,23,5,78));
        assertArrayEquals(new Integer[]{5,6,7,8,2,4,3,23,78}, flattenList.toArray());
    }


    @Test
    public void whenArrayContainsNullElementShouldThrowException() {
        Object [] array = {1,3,null};

        try {
            ArrayFlattener.flatten(array);
        } catch (IllegalArgumentException e){
            assertNotNull(e);
        }
    }


    @Test
    public void whenObjectInArrayIsNotAnIntegerNorAnotherArrayShouldThrowException() {
        Object [] array = {1,3,""};

        try {
            ArrayFlattener.flatten(array);
        } catch (IllegalArgumentException e){
            assertNotNull(e);
        }
    }

}