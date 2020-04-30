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
    
    public static int getMin(int arr[]) 
    { 
        int minInd = 0; 
        for (int i = 1; i < N; i++) 
            if (arr[i] < arr[minInd]) 
                minInd = i; 
        return minInd; 
    } 
      
    public static int getMax(int arr[]) 
    { 
        int maxInd = 0; 
        for (int i = 1; i < N; i++) 
            if (arr[i] > arr[maxInd]) 
                maxInd = i; 
        return maxInd; 
    } 
    
    public static int minOf2(int x, int y) 
    { 
        return (x < y) ? x: y; 
    } 
      
    public static void minCashFlowRec(int amount[], List<String> participantsIds , List<SettledChaque> resultList, List<String> banksNames, List<String> branchesNames) 
    { 
        int mxCredit = getMax(amount), mxDebit = getMin(amount); 
        if (amount[mxCredit] == 0 && amount[mxDebit] == 0) 
            return; 
        int min = minOf2(-amount[mxDebit], amount[mxCredit]); 
        amount[mxCredit] -= min; 
        amount[mxDebit] += min; 
      
        System.out.println("Branch " + participantsIds.get(mxDebit) + " from Bank : "+ banksNames.get(mxDebit) + " pays " + min 
                                + " to " + "Branch " + participantsIds.get(mxCredit) + " from Bank : "+banksNames.get(mxCredit)); 
        
        SettledChaque check = new SettledChaque(banksNames.get(mxDebit),branchesNames.get(mxDebit),participantsIds.get(mxDebit),banksNames.get(mxCredit),branchesNames.get(mxCredit),participantsIds.get(mxCredit),min); 
        
        resultList.add(check);
        minCashFlowRec(amount,participantsIds,resultList,banksNames,branchesNames); 
    } 

    public static List<SettledChaque> minCashFlow(int graph[][], List<String> participantsIds , List<String> BanksNames , List<String>BranchesNames) 
    { 
        int amount[]=new int[N]; 
      
        for (int p = 0; p < N; p++) 
        for (int i = 0; i < N; i++) 
            amount[p] += (graph[i][p] - graph[p][i]);
        List<SettledChaque> resultList = new ArrayList<SettledChaque>();
        minCashFlowRec(amount,participantsIds,resultList,BanksNames,BranchesNames); 
        return resultList; 
    } 
  
}
