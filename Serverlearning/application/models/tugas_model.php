<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Tugas_model extends CI_Model{
    
    //Name of table
    public $table_tugas = 'tugas';
    public $table_soal = 'soal_tugas';
    
    //Column of table tugas
    public $id_tugas = 'id_tugas';
    public $judul_tugas = 'judul_tugas';
    public $catatan = 'catatan';
    
    //Column of table soal
    public $id_soal = 'id_soal';
    public $isi_soal = 'isi_soal';

    public function __construct() {
        parent::__construct();
    }
    
    /*
     * @params : data_tugas = array with key value, contoh: array('judul_tugas' => 'judul')
     *           data_soal = array isi_soal, contoh: array('soal1','soal2')
     * @result boolean, trus if sukses, false if failed
     */
    public function add($data_tugas, $data_soal) {
        $result = false;
        $this->db->trans_begin();
        if(!$this->db->insert($this->table_tugas, $data_tugas)) {
            $this->db->trans_rollback();
            return $result;
        }
        
        $id = $this->db->insert_id();
        
        foreach ($data_soal as $isi_soal) {
            $data = array($this->isi_soal, $isi_soal,
                          $this->id_tugas, $id);
            if(!$this->db->insert($this->table_soal, $data)) {
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
    
    /*
     * @params : id_tugas
     *           data_tugas = array with key value, contoh: array('judul_tugas' => 'judul')
     *           data_soal = array 2 dimensi dari soal, contoh: [array('id_soal' => id, 'isi_soal' => id)]
     * @result boolean, trus if sukses, false if failed
     */
    public function update($id_tugas, $data_tugas, $data_soal) {
        $result = false;
        $this->db->trans_begin();
        if(!$this->db->update($this->table_tugas, $data_tugas, array($this->id_tugas => $id_tugas))){
            $this->db->trans_rollback();
            return $result;
        }
        
        foreach ($data_soal as $soal) {
            if( !$this->db->update($this->table_soal, 
                    array($this->isi_soal => $soal['isi_soal']), 
                    array('id_soal' => $soal['id_soal'], 'id_tugas' => $soal['id_tugas'])) ){
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
    
    public function delete($id_tugas) {
        $this->db->where($this->id_tugas, $id_tugas);
        return $this->db->delete($this->table_tugas);
    }
    
    public function get() {
        
    }

}