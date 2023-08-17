package com.projetoslabex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projetoslabex.academia.R;
import com.projetoslabex.model.ClienteTeste;

import java.util.List;


public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.MyViewHolder> {

    private List<ClienteTeste> clientes;
    private Context context;

    public AdapterCliente(List<ClienteTeste> cliente, Context context) {
        this.clientes = cliente;
        this.context = context;
    }

    public void removerPessoa(int position) {
        clientes.remove(position);
        notifyItemRemoved(position);
    }

    // Método para atualizar a lista de clientes após o filtro
    public void setClientes(List<ClienteTeste> cliente) {
        this.clientes = cliente;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_listar_clientes, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        ClienteTeste cliente = this.clientes.get(i);
        holder.textNomePessoa.setText("Nome: " + cliente.getNome());
        holder.textEndereco.setText("Endereço: " + cliente.getEndereco());
        holder.textEmail.setText("Email: " + cliente.getEmail());
        holder.textTurno.setText("Turno: " + cliente.getTurno());
        holder.textDataNascimento.setText("Data de Nascimento: " + cliente.getDataNascimento());



        /*

        // Verificar o status e definir o plano de fundo do item
        if (pessoa.getStatusPagamento().equals("Realizado")) {
            holder.itemView.setBackgroundResource(R.color.green);
        } else if (pessoa.getStatusPagamento().equals("Pendente")) {
            holder.itemView.setBackgroundResource(R.color.red);
        }

        */



    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textNomePessoa, textEndereco, textEmail, textTurno, textDataNascimento;

        public MyViewHolder(View itemView) {
            super(itemView);

            textNomePessoa = itemView.findViewById(R.id.textNomePessoa);
            textEndereco = itemView.findViewById(R.id.textEndereco);
            textEmail = itemView.findViewById(R.id.textEmail);
            textTurno = itemView.findViewById(R.id.textTurno);
            textDataNascimento = itemView.findViewById(R.id.textDataNascimento);

        }
    }
}
