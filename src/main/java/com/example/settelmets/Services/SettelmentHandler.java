package com.example.settelmets.Services;

import java.util.ArrayList;
import java.util.List;

import com.example.settelmets.Models.SettledChaque;

public class SettelmentHandler {
    // Number of participants in the settlement 
    private static int N ; 
     
    public static void setNumberOfParticipants(int numberOfPerticipants ) {
    	N = numberOfPerticipants ;
    }
    
    public int setNumberOfParticipants() {
    	return N ; 
    }
    
    public static int getMin(long arr[]) 
    { 
        int minInd = 0; 
        for (int i = 1; i < N; i++) 
            if (arr[i] < arr[minInd]) 
                minInd = i; 
        return minInd; 
    } 
      
    public static int getMax(long arr[]) 
    { 
        int maxInd = 0; 
        for (int i = 1; i < N; i++) 
            if (arr[i] > arr[maxInd]) 
                maxInd = i; 
        return maxInd; 
    } 
    
    public static long minOf2(long x, long y) 
    { 
        return (x < y) ? x: y; 
    } 
      
    public static void minCashFlowRec(long amount[], List<String> participantsIds , List<SettledChaque> resultList, List<String> banksNames, List<String> branchesNames,long[] resultArrayFromMCF) 
    { 
        int mxCredit = getMax(amount), mxDebit = getMin(amount); 
        if (amount[mxCredit] == 0 && amount[mxDebit] == 0) 
            return; 
        long min = minOf2(-amount[mxDebit], amount[mxCredit]); 
        amount[mxCredit] -= min; 
        amount[mxDebit] += min; 
      
        System.out.println("Branch " + participantsIds.get(mxDebit) + " from Bank : "+ banksNames.get(mxDebit) + " pays " + min 
                                + " to " + "Branch " + participantsIds.get(mxCredit) + " from Bank : "+banksNames.get(mxCredit)); 
        resultArrayFromMCF[mxDebit] -= min ; 
        resultArrayFromMCF[mxCredit] += min ; 
        SettledChaque check = new SettledChaque(banksNames.get(mxDebit),branchesNames.get(mxDebit),participantsIds.get(mxDebit),banksNames.get(mxCredit),branchesNames.get(mxCredit),participantsIds.get(mxCredit),min); 
        
        resultList.add(check);
        minCashFlowRec(amount,participantsIds,resultList,banksNames,branchesNames,resultArrayFromMCF); 
    } 

    public static List<SettledChaque> minCashFlow(long graph[][], List<String> participantsIds , List<String> BanksNames , List<String>BranchesNames,long[] resultArrayFromMCF) 
    { 
        long amount[]=new long[N]; 
      
        for (int p = 0; p < N; p++) 
        for (int i = 0; i < N; i++) 
            amount[p] += (graph[i][p] - graph[p][i]);
        List<SettledChaque> resultList = new ArrayList<SettledChaque>();
        minCashFlowRec(amount,participantsIds,resultList,BanksNames,BranchesNames,resultArrayFromMCF); 
        return resultList; 
    } 
    
    public static List<SettledChaque> invokeSettlementSequence(long graph[][], List<String> participantsIds , List<String> BanksNames , List<String>BranchesNames) {
    	 long[][] checkArray = new long[N][2]; 
    	 List<SettledChaque> resultList = new ArrayList<SettledChaque>();    	
         for(int i = 0 ; i <N ; i++){
             checkArray[i][0] = 0 ; 
             checkArray[i][1] = 0 ; 
         }
         for(int i = 0 ; i< N ; i++){
             for(int j = 0 ; j < N ; j ++){
                 checkArray[i][0] += graph[i][j];
                 checkArray[j][1] += graph[i][j];
             }
         }
         long[] compareArray = new long[N]; 
         compareArray = prepareCheckArrayForComparison(checkArray);
         System.out.println("proceeding to MCF Algo at : " + System.currentTimeMillis());
         double start =  System.currentTimeMillis() ; 
         long[] resultArrayFromMCF = new long[N];
         resultList =  minCashFlow(graph,participantsIds ,BanksNames ,BranchesNames,resultArrayFromMCF) ;
         double finish = System.currentTimeMillis() ; 
         System.out.println("exit time : " + System.currentTimeMillis());
         System.out.println("time span : "+ (finish - start));     
         if(checkCompareArraysResults(compareArray,resultArrayFromMCF)){
             System.out.println("Settlement Service : "
             		+ "Settlement Operations finished -- "
             		+ "data check passed ! proceed to data storage and reporting");
             return resultList ; 
         }else{
             System.out.println("Settlement Service : "
             		+ "Settlement Operations Failed duo to data check error!!"
             		+ "some checks will not be payed correctly -- "
             		+ "rolling back all transactions !");
             return null ;
         }
    }

    
	public static void printArray(long[][] arr){
        System.out.println("generated array : ");
      for(int i = 0 ; i < N ; i++){
      for(int j = 0 ; j < N ; j++){
          System.out.print(arr[i][j] +" ");
          }
          System.out.println("");
      	}
	  }
	
	public static void printCheckArrays(long[] checkArray){
	      for(int i = 0 ; i < N ; i++){
	          System.out.print(checkArray[i] +" ");
	      }
	  }
	  
	public static void pringMultiDimensionalCheckArray(long[][] checkArray){
	      for(int i = 0 ; i < N ; i++){
	          System.out.println(checkArray[i][0]+" "+checkArray[i][1]);
	      }
	  }
	  
	public static long[] prepareCheckArrayForComparison(long[][] checkArray){
	      long[] resultArray = new long[N]; 
	      for(int i = 0 ; i < N ; i ++ ){
	          resultArray[i] = checkArray[i][1] - checkArray[i][0] ; 
	      }
	      return resultArray ; 
	  }
	  
	public static boolean checkCompareArraysResults(long[] compareArrayFromMain , long[] compareArrayFromMCFResults){ 
	      for(int i = 0 ; i < N ; i++  ){
	          if(compareArrayFromMain[i] != compareArrayFromMCFResults[i])
	              return false ; 
	      }
	      return true  ; 
	  }
	
    
    
}
