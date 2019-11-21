package com.app.alevant.DMT.beneficiarylist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.alevant.R;

import java.util.ArrayList;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder> {
  private ArrayList<Beneficiary> list;
  private ClickListener clickListener;

  public BeneficiaryAdapter(ArrayList<Beneficiary> list) {
    this.list = list;
  }

  @NonNull
  @Override
  public BeneficiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.row_bene_list, parent, false);
    return new BeneficiaryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final BeneficiaryViewHolder viewHolder, int position) {
    Beneficiary beneficiary = list.get(position);

    viewHolder.tv_bank_name.setText(beneficiary.getBENE_BANKNAME());
    viewHolder.tv_bene_name.setText(beneficiary.getBENE_NAME());
    viewHolder.tv_bene_ifsc_code.setText(beneficiary.getBANKIFSC_CODE());
    viewHolder.tv_account_no.setText(beneficiary.getBANK_ACCOUNTNO());

    if (beneficiary.getBENE_OTP_VERIFIED().equalsIgnoreCase("true")) {
      viewHolder.tv_validate.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.fund, 0, 0);
    } else {
      viewHolder.tv_validate.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.cancel, 0, 0);
    }

    if (beneficiary.getIS_BENEVERIFIED().equalsIgnoreCase("true")) {
      viewHolder.tv_validate.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ait, 0, 0);
    } else {
      viewHolder.tv_validate.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.cancel, 0, 0);
    }

  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class BeneficiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv_bank_name, tv_bene_name, tv_bene_ifsc_code, tv_account_no, tv_fund_transfer, tv_validate, tv_verify, tv_cancel;


    private BeneficiaryViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_bank_name = itemView.findViewById(R.id.tv_bank_name);
      tv_bene_name = itemView.findViewById(R.id.tv_bene_name);
      tv_bene_ifsc_code = itemView.findViewById(R.id.tv_bene_ifsc_code);
      tv_account_no = itemView.findViewById(R.id.tv_account_no);

      tv_fund_transfer = itemView.findViewById(R.id.tv_fund_transfer);
      tv_validate = itemView.findViewById(R.id.tv_validate);
      tv_verify = itemView.findViewById(R.id.tv_verify);
      tv_cancel = itemView.findViewById(R.id.tv_cancel);

      tv_fund_transfer.setOnClickListener(this);
      tv_validate.setOnClickListener(this);
      tv_verify.setOnClickListener(this);
      tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (clickListener != null) {
        clickListener.itemClicked(view, getLayoutPosition());
      }
    }
  }

  public interface ClickListener {
    public void itemClicked(View view, int position);
  }

  public void setClickListener(ClickListener clickListener) {
    this.clickListener = clickListener;
  }
}
