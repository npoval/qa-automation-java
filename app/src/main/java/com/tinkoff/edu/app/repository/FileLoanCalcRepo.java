package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.request.LoanRequest;
import com.tinkoff.edu.app.request.LoanType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.*;

public class FileLoanCalcRepo implements LoanCalcRepository{
    Map<UUID, LoanRequestResponse> loanRequestResponseMap = new HashMap<>();
    List<String> mLoanRequestResponse = new ArrayList<>();
    private Path fileRepo = Path.of("repo.txt");

    public void setLoanRequestResponseFile() throws IOException {
        loanRequestResponseMap.forEach((uuid, loanRequestResponse) -> mLoanRequestResponse.add(uuid + ":::" + loanRequestResponse.getLoanResponse().getResponseType()
                + ":::" + loanRequestResponse.getLoanRequest().getType() + "\n"));
        Files.write(fileRepo, mLoanRequestResponse, APPEND, CREATE, WRITE);
    }

    public void setLoanRequestResponseMap(UUID uuid, LoanRequestResponse loanRequestResponse) {
        this.loanRequestResponseMap.put(uuid, loanRequestResponse);
    }

    public String getStatusByUuid(UUID uuid) throws IOException {
        final String[] status = new String[1];
        Files.lines(fileRepo)
                .filter(line -> line.contains(String.valueOf(uuid)))
                .forEach(line -> status[0] = line.split(":::")[1]);
        return status[0];
    }

    public long getCountLoanByLoanType(LoanType loanType) throws IOException {
        long count = Files.lines(fileRepo)
                .filter(line -> line.contains(String.valueOf(loanType)))
                .count();
         return count;
    }

    @Override
    public UUID save(LoanRequest request) {
        return UUID.randomUUID();
    }
}
