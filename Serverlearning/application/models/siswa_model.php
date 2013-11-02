<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Siswa_model extends CI_Model{
    
    public $table = 'siswa';
    public $id_siswa = 'id_siswa';
    public $username = 'username';
    public $password = 'password';
    public $nama = 'nama';
    public $jenis_kelamin = 'jenis_kelamin';
    public $alamat = 'alamat';

    public function __construct() {
        parent::__construct();
    }
    
    public function get() {
        $result = array();
        $this->db->order_by($this->id_siswa, "asc");
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
    
    public function get_by_id($id_siswa) {
        $this->db->where($this->id_siswa, $id_siswa);
        $this->db->limit(1);
        $query = $this->db->get($this->table);
        return ($query->num_rows() > 0) ? $query->row() : false;;
    }

    public function add($data) {
        $this->db->insert($this->table, $data);
        return $this->db->insert_id();
    }
    
    public function update($id_siswa, $data) {
        $this->db->where($this->id_siswa, $id_siswa);
        $this->db->update($this->table, $data);
        return $this->db->affected_rows();
    }
    
    public function delete($id_siswa) {
        $this->db->where($this->id_siswa, $id_siswa);
        return $this->db->delete($this->table);
    }

}