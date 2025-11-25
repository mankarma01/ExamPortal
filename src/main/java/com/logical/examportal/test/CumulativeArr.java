package com.logical.examportal.test;

public class CumulativeArr {

    public static void main(String[] args) {
        CumulativeArr cr = new CumulativeArr();
       //int[] arr = cr.getCumulativeSum(new int[]{1, 3, 5, 7, 9});
      // int[] arr = cr.checkSumExists(new int[]{2,7,11,15}, 9);


       int profit = cr.maxProfit(new int[]{7,6,4,3,6, 12});

        System.out.println(profit);

      /* if(arr == null){
           System.out.println("array is null");
           return;
       }
       for (int cur : arr) {
           System.out.print(cur  + ", ");
       }*/

    }

    public int maxProfit(int[] prices) {

        int lowestNum = prices[0];
        int highestProfit = 0;
        for(int i = 0; i<prices.length-1; i++){
            if(prices[i]<lowestNum){
                lowestNum = prices[i];
                for(int j = i+1; j<prices.length; j++){
                    if(prices[j]>lowestNum && prices[j]>highestProfit ){
                        highestProfit = prices[j];
                    }
                }
            }

        }
        System.out.println(lowestNum + " dsfsf "  + highestProfit );

       if(highestProfit-lowestNum <0){
            return 0;
        }

    return highestProfit-lowestNum;
    }



    int[] checkSumExists(int[] arr, int target){

        if(2>arr.length || arr.length>10000){
           return null;
        }

        for(int i=0; i<arr.length-1; i++){
            for(int j=i+1; j<arr.length; j++){
                if(arr[i] + arr[j] == target){
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

        int[] getCumulativeSum (int[] arr) {

            int[] cumulativeSumArr = new int[arr.length];
            int cumulativeSum = 0;
            for(int i = 0; i<arr.length; i++){
                cumulativeSum = cumulativeSum + arr[i];
                cumulativeSumArr[i] = cumulativeSum;
            }
            return cumulativeSumArr;

        }

}
