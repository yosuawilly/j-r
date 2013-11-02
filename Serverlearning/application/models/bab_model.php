<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Bab_model extends CI_Model {
    
    public $table = 'bab';
    public $id_bab = 'id_bab';
    public $label_bab = 'label_bab';
    public $judul_bab = 'judul_bab';

    public function __construct() {
        parent::__construct();
    }
    
    public function get() {
        $result = array();
        $this->db->order_by($this->id_bab, "asc");
        $query = $this->db->get($this->table);
        if($query){
            $result['num_rows'] = $query->num_rows();
            $result['rows'] = $query->result_array();
        } else {
            $result['num_rows'] = 0;
            $result['rows'] = array();
        }
//        return $query->result_array();
        return $result;
    }
    
    public function get_by_id($id_bab) {
        $this->db->where($this->id_bab, $id_bab);
        $this->db->limit(1);
        $query = $this->db->get($this->table);
        return ($query->num_rows() > 0) ? $query->row() : false;;
    }
    
    public function add($data) {
        $result = array();
        $this->db->insert($this->table, $data);
        if($this->db->_error_message()){
            $result['error'] = $this->db->_error_message();
        } else $result['error'] = '';
        $result['result'] = $this->db->insert_id();
        return $result;
    }
    
    public function update($id_bab, $data) {
        $this->db->where($this->id_bab, $id_bab);
        $this->db->update($this->table, $data);
        return $this->db->affected_rows();
    }
    
    public function delete($id_bab) {
        $this->db->where($this->id_bab, $id_bab);
        return $this->db->delete($this->table);
    }

}