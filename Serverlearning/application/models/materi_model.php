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
        $this->db->order_by($this->id_materi, "asc");
        $query = $this->db->get($this->table);
        if($query){
            $result['num_rows'] = $query->num_rows();
            $result['rows'] = $query->result_array();
        } else {
            $result['num_rows'] = 0;
            $result['rows'] = array();
        }
        return $result;
    }
    
    public function add($data) {
        $this->db->insert($this->table, $data);
        return $this->db->insert_id();
    }
    
    public function update($id_materi, $data) {
        $this->db->where($this->id_materi, $id_materi);
        $this->db->update($this->table, $data);
        return $this->db->affected_rows();
    }
    
    public function delete($id_materi) {
        $this->db->where($this->id_materi, $id_materi);
        return $this->db->delete($this->table);
    }

}