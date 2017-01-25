package com.revature.caliber.gateway;

import java.util.List;

import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.Trainer;

/**
 * Gathers data from appropriate services and
 * combines the model to deliver to the view.
 * 
 * Reduces complexity compared to calling each
 * service individually throughout the application.
 *
 */
public interface ApiGateway {

	/**
	 * Get all Batches for a given Trainer.
	 * @param trainer
	 * @return
	 */
	public List<Batch> getBatches(Trainer trainer);
	
	/**
	 * Get all Batches in the company. Useful for
	 * VP and QC roles to aggregate company data.
	 * 
	 * @return
	 */
	//TODO public List<Batch> getBatches();
	
	/**
	 * Get all Batches currently within the window
	 * of their training period. 
	 * @return
	 */
	// TODO public List<Batch> getCurrentBatches();

	//Trainee
	/**
	 * Creates new trainee
	 * @param trainee trainee to create
	 */
	void createTrainee(Trainee trainee);

	/**
	 * Update trainee's info
	 * @param trainee trainee to update (with new info)
	 */
	void updateTrainee(Trainee trainee);

	/**
	 * Get trainee by id.
	 * @param id id of trainee to get
	 * @return Trainee object, or null if trainee with id doesn't exist
	 */
	Trainee getTrainee(Integer id);

	/**
	 * Get trainee by full name
	 * @param name name of trainee to get
	 * @return Trainee object or null if trainee with name doesn't exist
	 */
	Trainee getTrainee(String name);

	/**
	 * Get list of trainees for a certain batch
	 * @param batchId id of the batch
	 * @return list of trainees or an empty list if there is no batch (null?)
	 */
	List<Trainee> getTraineesInBatch(Integer batchId);

	/**
	 * Delete a trainee
	 * @param trainee trainee to delete
	 */
	void deleteTrainee(Trainee trainee);

	//end of Trainee Service
	
	
}