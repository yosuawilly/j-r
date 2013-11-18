<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Version_model extends CI_Model{

    public $table = 'table_version';
    public $nama_table = 'nama_table';
    public $version = 'version';

    public function __construct() {
        parent::__construct();
    }

    public function get() {
        $this->db->order_by($this->nama_table, "asc");
        $query = $this->db->get($this->table);
        return $query->result_array();
    }

    public function get_by_nama_table($nama_table) {
        $this->db->where($this->nama_table, $nama_table);
        $this->db->limit(1);
        $query = $this->db->get($this->table);
        return ($query->num_rows() > 0) ? $query->row() : false;
    }

}