<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Materi_model extends CI_Model{
    
    public $table = 'materi';
    public $id_materi = 'id_materi';
    public $judul = 'judul';
    public $isi_materi = 'isi_materi';
    public $id_bab = 'id_bab';
    public $semester = 'semester';
    public $url = 'url';

    public function __construct() {
        parent::__construct();
    }
    
    public function get() {
        $result = array();
        $strSQL = 'select m.*,b.label_bab,b.judul_bab from materi m, bab b where m.id_bab=b.id_bab order by id_materi asc';
//        $this->db->order_by($this->id_materi, "asc");
//        $query = $this->db->get($this->table);
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
    
    public function get_by_id($id_materi) {
        $strSQL = 'select m.*,b.label_bab,b.judul_bab from 
            materi m, bab b where m.id_bab=b.id_bab and m.id_materi = ? limit 1';
        $query = $this->db->query($strSQL, $id_materi);
        return ($query->num_rows() > 0) ? $query->row() : false;
    }

    public function add($data) {
        $result = array();
        $this->db->insert($this->table, $data);
        if($this->db->_error_message()){
            $result['error'] = $this->db->_error_message();
            $result['result'] = false;
        } else {
            $result['error'] = '';
            $result['result'] = $this->db->insert_id();
        }
        return $result;
    }
    
    public function update($id_materi, $data) {
        $this->db->where($this->id_materi, $id_materi);
        $this->db->update($this->table, $data);
        return $this->db->affected_rows();
    }
    
    public function delete($id_materi) {
        $result = array();
        $strSQL = 'select judul from materi where id_materi = ?';
        $query = $this->db->query($strSQL, $id_materi);
        if($query){
            $result['judul'] = $query->row()->judul;
        } else {
            $result['judul'] = '';
        }
        $this->db->where($this->id_materi, $id_materi);
        $result['result'] = $this->db->delete($this->table);
        return $result;
    }

}