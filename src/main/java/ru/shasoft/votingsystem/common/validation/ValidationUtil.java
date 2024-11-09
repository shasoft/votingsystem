package ru.shasoft.votingsystem.common.validation;

import lombok.experimental.UtilityClass;
import ru.shasoft.votingsystem.app.AuthUser;
import ru.shasoft.votingsystem.common.HasId;
import ru.shasoft.votingsystem.common.error.IllegalRequestDataException;
import ru.shasoft.votingsystem.user.model.Role;

import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {
    public static final int VOTE_END_HOUR = 11;

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkUpdateLike(AuthUser authUser) {
        if (!authUser.hasRole(Role.USER)) {
            throw new IllegalRequestDataException("No permission to perform the operation");
        }
        final LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(VOTE_END_HOUR, 0))) {
            throw new IllegalRequestDataException("It's too late to vote.");
        }
    }
}