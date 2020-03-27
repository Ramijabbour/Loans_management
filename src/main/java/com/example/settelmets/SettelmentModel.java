package com.example.settelmets;

public class SettelmentModel {

    // Number of participants in the settlement 
    private int N ; 
     
    public void setNumberOfParticipants(int numberOfPerticipants ) {
    	this.N = numberOfPerticipants ;
    }
    
    public int setNumberOfParticipants() {
    	return N ; 
    }
    
    public int getMin(int arr[]) 
    { 
        int minInd = 0; 
        for (int i = 1; i < N; i++) 
            if (arr[i] < arr[minInd]) 
                minInd = i; 
        return minInd; 
    } 
      
    public int getMax(int arr[]) 
    { 
        int maxInd = 0; 
        for (int i = 1; i < N; i++) 
            if (arr[i] > arr[maxInd]) 
                maxInd = i; 
        return maxInd; 
    } 
    
    public int minOf2(int x, int y) 
    { 
        return (x < y) ? x: y; 
    } 
      
    public void minCashFlowRec(int amount[]) 
    { 
        int mxCredit = getMax(amount), mxDebit = getMin(amount); 
      
        // If both amounts are 0, then  
        // all amounts are settled 
        if (amount[mxCredit] == 0 && amount[mxDebit] == 0) 
            return; 
      
        // Find the minimum of two amounts 
        int min = minOf2(-amount[mxDebit], amount[mxCredit]); 
        amount[mxCredit] -= min; 
        amount[mxDebit] += min; 
      
        // If minimum is the maximum amount to be 
        System.out.println("Person " + mxDebit + " pays " + min 
                                + " to " + "Person " + mxCredit); 

        minCashFlowRec(amount); 
    } 

    public void minCashFlow(int graph[][]) 
    { 
        int amount[]=new int[N]; 
      
        for (int p = 0; p < N; p++) 
        for (int i = 0; i < N; i++) 
            amount[p] += (graph[i][p] - graph[p][i]); 
        minCashFlowRec(amount); 
    } 
    

}
