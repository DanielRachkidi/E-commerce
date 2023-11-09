using E_commerce.DataModel;

namespace E_commerce.Repositories
{
    public interface IUserPaymentRepository
    {
        bool FindByUser(User user);
        UserPayment AddUserPayment(UserPayment userPayment);
        UserPayment UpdateUserPayment(UserPayment userPayment);
        UserPayment GetUserPaymentById(int id);
        object CreateUserPaymentInfo(bool user, UserPayment userPayment);
        User FindByUser(int userId);
        bool ValidatePayment(UserPayment userPayment);
    }
}
