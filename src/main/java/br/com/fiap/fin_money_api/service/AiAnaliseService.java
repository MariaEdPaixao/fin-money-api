package br.com.fiap.fin_money_api.service;

import br.com.fiap.fin_money_api.model.TransactionType;
import br.com.fiap.fin_money_api.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

@Service
public class AiAnaliseService {

    //@Autowired -> injeta o objeto diretamente
    private ChatClient chatClient;
    private TransactionRepository transactionRepository;

    // injeção de dependencia do chatClient -> permite que eu possa na hora de injetar, mudar alguns comportamentos
    // aqui coloco mudanças gerais, então sempre que eu usar essa dependencia, vai vir com as especificações que coloquei
    public  AiAnaliseService(ChatClient.Builder chatClientBuilder, TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
        this.chatClient = chatClientBuilder
                            .defaultSystem("Responda sempre em {lang}. Quando fizer uma análise, seja objetivo e responda no máximo 80 caracteres")
                            .defaultOptions(ChatOptions.builder().temperature(0.5).topP(1.0).build())
                            .build();
                                            // {lang} -> informa que isso é uma variável, do nome lang
    }
    public String getExpenseAnalise(String lang){
        var expenses = transactionRepository.findByType(TransactionType.EXPENSE);
        //transformar uma lista de obj em json
        var objectMapper = new ObjectMapper();
        String json = "";
        try{
            json = objectMapper.writeValueAsString(expenses);
            // pode ser prompt de usuario -> msg que o user manda no chat
            // msg do sistema -> parametros que ajustamos para configurar geral como o nosso sitema tem que se comportar
            // .system(sp -> sp.param("lang", lang)) -> utilizo o parametro global da injeção, porem substituo o parametro que o sistema de injeção deixou global e mudo a variavel com o que chegou por parametro do metodo
            return chatClient
                    .prompt()
                    .user("faça uma análise das minhas despesas: " + json)
                    .system(sp -> sp.param("lang", lang))
                    .call()
                    .content(); // pego o conteudo da resposta

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

       return "erro ao converter para json";
    }
}
