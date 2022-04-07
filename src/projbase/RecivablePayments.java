/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projbase;

public class RecivablePayments {
  private int EmSsn;
  private int PId;

    public RecivablePayments(int EmSsn, int PId) {
        this.EmSsn = EmSsn;
        this.PId = PId;
    }

    public int getEmSsn() {
        return EmSsn;
    }

    public void setEmSsn(int EmSsn) {
        this.EmSsn = EmSsn;
    }

    public int getPId() {
        return PId;
    }

    public void setPId(int PId) {
        this.PId = PId;
    }
  
}