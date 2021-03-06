package in.projecteka.user.filters;

import in.projecteka.user.IdentifierUtils;
import in.projecteka.user.model.Identifier;
import in.projecteka.user.model.IdentifierType;
import in.projecteka.user.model.User;
import io.vertx.core.json.JsonArray;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ABPMJAYIdFilter implements FilterStrategy<List<Identifier>> {
    @Override
    public Mono<List<User>> filter(List<User> users, List<Identifier> identifiers) {
        if (IdentifierUtils.isIdentifierTypePresent(identifiers, IdentifierType.ABPMJAYID)) {
            String abpmjayId = IdentifierUtils.getIdentifierValue(identifiers, IdentifierType.ABPMJAYID);
            users = users.stream().filter(row -> isMatchingABPMJAYId(row.getUnverifiedIdentifiers(), abpmjayId))
                    .collect(toList());
        }
        return users.isEmpty() ? Mono.empty() : Mono.just(users);
    }

    private boolean isMatchingABPMJAYId(JsonArray unverifiedIdentifiers, String abpmjayid) {
        if (unverifiedIdentifiers == null || unverifiedIdentifiers.isEmpty()) {
            return false;
        }
        return IntStream.range(0, unverifiedIdentifiers.size())
                .mapToObj(unverifiedIdentifiers::getJsonObject)
                .anyMatch(identifier ->
                        identifier.getValue("type").equals(IdentifierType.ABPMJAYID.name())
                                && identifier.getValue("value").equals(abpmjayid));
    }
}
