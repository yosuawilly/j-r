/**
 * 
 */
package com.LearningKimia.restfull;

/**
 * @author Dwidasa
 *
 */
public interface AsyncTaskCompleteListener<T> {
	public void onTaskComplete(T... params);
}
