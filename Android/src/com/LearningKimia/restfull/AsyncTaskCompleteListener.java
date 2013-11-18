/**
 * 
 */
package com.LearningKimia.restfull;

/**
 * @author Yosua Willy
 *
 */
public interface AsyncTaskCompleteListener<T> {
	public void onTaskComplete(T... params);
}
