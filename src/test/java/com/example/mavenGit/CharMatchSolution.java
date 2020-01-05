package com.example.mavenGit;

/**
 * @version 1.0
 * @auth leibao
 * @date 2019/12/21
 */
public class CharMatchSolution {


		public int longestValidParentheses(String s) {
			if(null==s){
				return 0;
			}
			char[] array = s.toCharArray();
			System.out.print(array);
			// if(null==array || array.lenth==0){
			//     return 0;
			// }
			int maxLeft = maxMatchLen(array,0,array.length,1,'(');
			int maxRight = maxMatchLen(array,array.length-1,-1,-1,')');

			return Math.max(maxLeft,maxRight);
		}
		public static int maxMatchLen(char[] array,int begin,int end,int step,char ch){
			int matchSum = 0,maxLen=0,currentLen = 0,validLen=0;
			int i=begin; //begin:inclusive
			for(;i!=end;i+=step){
				//end:exclusive
				matchSum += (array[i]==ch?1:-1);
				currentLen ++;
				//matched
				if(matchSum==0){
					validLen = currentLen;//valid count from last time
					continue;
				}
				//first char that not match:reset var set
				if(matchSum<0){
					maxLen = Math.max(maxLen,validLen);
					currentLen=0;//index from last time
					validLen=0;
					matchSum=0;
				}
				//if matchSum>0:go on

			}
			maxLen = Math.max(maxLen,validLen);
			return maxLen;
		}
	}

