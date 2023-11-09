using E_commerce.DataModel;
using E_commerce.Repositories;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace E_commerce.Service
{
    public class UserPaymentService
    {
        private readonly UserPaymentRepository _userPaymentRepository;

        public UserPaymentService(UserPaymentRepository userPaymentRepository)
        {
            _userPaymentRepository = userPaymentRepository;
        }

        public async Task<List<UserPayment>> GetAllUserPaymentsAsync()
        {
            return await _userPaymentRepository.GetAllUserPaymentsAsync();
        }

        public async Task<UserPayment> GetUserPaymentByIdAsync( int paymentId)
        {
            return await _userPaymentRepository.GetUserPaymentByIdAsync(paymentId);
        }

        public async Task<int> AddUserPaymentAsync(int userId, UserPayment userPayment)
        {
            return await _userPaymentRepository.AddUserPaymentAsync(userPayment);
        }

        public async Task UpdateUserPaymentAsync(int userId, UserPayment userPayment)
        {
            await _userPaymentRepository.UpdateUserPaymentAsync(userPayment);
        }

        internal Task AddUserPaymentAsync(UserPayment userPayment)
        {
            throw new NotImplementedException();
        }
    }
}
