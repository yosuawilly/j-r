<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/**
 * Description of upload_tugas_model
 *
 * @author LenovoDwidasa
 */
class Upload_tugas_model extends CI_Model{
    
    public $table = 'upload_tugas';
    public $id_upload = 'id_upload';
    public $id_siswa = 'id_siswa';
    public $id_tugas = 'id_tugas';
    public $nama_file = 'nama_file';
    public $tgl_upload = 'tgl_upload';
    
    public function __construct() {
        parent::__construct();
    }
    
    public function get_all_upload_tugas(){
        $result = array();
        $strSQL = 'select ut.*,s.nama,t.judul_tugas from upload_tugas ut, siswa s, tugas t 
where ut.id_siswa=s.id_siswa and ut.id_tugas=t.id_tugas';
        
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
    
    public function get_upload_tugas_by_id_tugas($id_tugas) {
        $result = array();
        $strSQL = 'select ut.*,s.nama,t.judul_tugas from upload_tugas ut, siswa s, tugas t 
where ut.id_siswa=s.id_siswa and ut.id_tugas=t.id_tugas and ut.id_tugas='.$id_tugas;
        
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
    
    public function get_upload_tugas_by_judul_tugas($judul_tugas) {
        $result = array();
        $strSQL = 'select ut.*,s.nama,t.judul_tugas from upload_tugas ut, siswa s, tugas t 
where ut.id_siswa=s.id_siswa and ut.id_tugas=t.id_tugas and t.judul_tugas=\''.$judul_tugas.'\'';
        
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
    
}
