<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Quiz_model extends CI_Model{
    
    //Name of table
    public $table_quiz = 'quiz';
    public $table_jawaban_quiz = 'jawaban_quiz';
    
    //Column of table quiz
    public $id_quiz = 'id_quiz';
    public $soal_quiz = 'soal_quiz';
    
    //Column of table jawaban_quiz
    public $id_jawaban = 'id_jawaban';
    public $jawaban = 'jawaban';
    public $benar = 'benar';

    public function __construct() {
        parent::__construct();
    }
    
    public function add($data_soal, $data_jawaban) {
        $result = array();
        $this->db->trans_begin();
        if(!$this->db->insert($this->table_quiz, $data_soal)) {
            $this->db->trans_rollback();
            $result['error'] = 'Error insert quiz';
            $result['result'] = false;
            return $result;
        }
        
        $id = $this->db->insert_id();
        
        foreach ($data_jawaban as $jawaban) {
            $strSQL = 'insert into jawaban_quiz(jawaban,id_quiz,benar) values (? , ? , ?)';
            $query = $this->db->query($strSQL, array($jawaban['jawaban'], $id, $jawaban['benar']));
            
            if(!$query) {
                $this->db->trans_rollback();
                $result['error'] = 'Error insert jawaban quiz';
                $result['result'] = false;
                return $result;
            }
        }
        
        if ($this->db->trans_status() === TRUE) {
            $this->db->trans_commit();
            $result['error'] = '';
            $result['result'] = true;
        } else {
            $this->db->trans_rollback();
            $result['error'] = $this->db->_error_message();
            $result['result'] = false;
        }
        
        return $result;
    }
    
    public function update($id_quiz, $data_soal, $data_jawaban){
        $result = false;
        $this->db->trans_begin();
        if(!$this->db->update($this->table_quiz, $data_soal, array($this->id_quiz => $id_quiz))){
            $this->db->trans_rollback();
            return $result;
        }

        $strSQL = 'delete from jawaban_quiz where id_quiz = ?';
        $query = $this->db->query($strSQL, $id_quiz);
        if(!$query){
            $this->db->trans_rollback();
            return $result;
        }
        
        foreach ($data_jawaban as $jawaban) {
            $strSQL = 'insert into jawaban_quiz(jawaban,id_quiz,benar) values (? , ?, ?)';
            $query = $this->db->query($strSQL, array($jawaban['jawaban'], $id_quiz, $jawaban['benar']));
            if(!$query) {
                $this->db->trans_rollback();
                return $result;
            }
        }
        
        if ($this->db->trans_status() === TRUE) {
            $this->db->trans_commit();
            $result = true;
        } else $this->db->trans_rollback();
        
        return $result;
    }
    
    public function delete($id_quiz) {
        $this->db->where($this->id_quiz, $id_quiz);
        return $this->db->delete($this->table_quiz);
    }
    
    public function get() {
        $result = array();
        $this->db->order_by($this->id_quiz, "asc");
        $query = $this->db->get($this->table_quiz);
        if($query){
            $result['num_rows'] = $query->num_rows();
            $result['rows'] = $query->result_array();
        } else {
            $result['num_rows'] = 0;
            $result['rows'] = array();
        }
        return $result;
    }
    
    public function get_by_id($id_quiz) {
        $this->db->where($this->id_quiz, $id_quiz);
        $this->db->limit(1);
        $query = $this->db->get($this->table_quiz);
        return ($query->num_rows() > 0) ? $query->row() : false;
    }
    
    public function get_jawaban($id_quiz) {
        $result = array();
        $this->db->where($this->id_quiz, $id_quiz);
        $query = $this->db->get($this->table_jawaban_quiz);
        if($query){
            $result['num_rows'] = $query->num_rows();
            $result['rows'] = $query->result_array();
        } else {
            $result['num_rows'] = 0;
            $result['rows'] = array();
        }
        return $result;
    }
    
    public function get_all_jawaban() {
        $result = array();
        $strSQL = 'select * from '.$this->table_jawaban_quiz;
        $query = $this->db->query($strSQL);
        if($query){
            $result['num_rows'] = $query->num_rows();
            $result['rows'] = $query->result_array();
        } else {
            $result['num_rows'] = 0;
            $result['rows'] = array();
        }
        return $result;
    }
    
}