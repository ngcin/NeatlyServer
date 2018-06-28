package com.geeyao.common.service;

import com.geeyao.common.bean.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class SequenceService {
    @Autowired
    private MongoOperations mongo;

    public long getNextSequence(String seqName, long defaultNext) {
        Update update = new Update().inc("next", 1);
        Sequence sequence = mongo.findAndModify(
                query(where("name").is(seqName)),
                update,
                options().returnNew(true).upsert(false),
                Sequence.class);
        if (sequence == null) {
            sequence = new Sequence();
            sequence.setName(seqName);
            sequence.setNext(defaultNext);
            mongo.insert(sequence);
            return sequence.getNext();
        }
        return sequence.getNext();
    }
}
