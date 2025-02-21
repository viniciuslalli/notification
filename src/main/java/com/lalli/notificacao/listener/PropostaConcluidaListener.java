package com.lalli.notificacao.listener;

import com.lalli.notificacao.constant.MensagemConstante;
import com.lalli.notificacao.domain.Proposta;
import com.lalli.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta){
        String mensagem;

        if (proposta.getAprovado() == true){
             mensagem = String.format(MensagemConstante.PROPOSTA_APROVADA, proposta.getUsuario().getNome());
        } else {
            mensagem = String.format(MensagemConstante.PROPOSTA_NEGADA, proposta.getUsuario().getNome());
        }

        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
