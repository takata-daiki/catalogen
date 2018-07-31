/**
 * LeetCode OJ: Intersection of Two Linked Lists
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    int countA, countB;
    ListNode ptrA, ptrB;
    
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) {
            return null;
        }
        
        countA = 0;
        countB = 0;
        ptrA = headA;
        ptrB = headB;
        
        while(ptrA != null) {
            ptrA = ptrA.next;
            countA++;
        }
        
        while(ptrB != null) {
            ptrB = ptrB.next;
            countB++;
        }
        
        ptrA = headA;
        ptrB = headB;
        
        while(countA != countB) {
            if(countA < countB) {
                ptrB = ptrB.next;
                countB--;
            }
            else if(countB < countA) {
                ptrA = ptrA.next;
                countA--;
            }
        }
        
        while(ptrA != ptrB && ptrA != null) {
            ptrA = ptrA.next;
            ptrB = ptrB.next;
        }
        
        return ptrA;
    }
}