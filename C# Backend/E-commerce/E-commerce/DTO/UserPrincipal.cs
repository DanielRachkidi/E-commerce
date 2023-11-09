using E_commerce.DataModel;
using Microsoft.Identity.Client;

namespace E_commerce.DTO
{
    public class UserPrincipal
    {
        private IAccount account;

        public int Id { get; }
        public string Username { get; }
        public DateTime LoginTime { get; }


        public UserPrincipal(int id, string username, DateTime loginTime)
        {
            Id = id;
            Username = username;
            LoginTime = loginTime;
        }

        public UserPrincipal(User user)
        {
            Id = user.Id;
            Username = user.Username;
            LoginTime = DateTime.Now;
        }

        public UserPrincipal(IAccount account)
        {
            this.account = account;
        }

        public string Subject()
        {
            return $"{Id},{Username},{LoginTime}";
        }

        public static UserPrincipal FromSubject(string subject)
        {
            string[] split = subject.Split(",");
            int id = int.Parse(split[0]);
            string username = split[1];
            DateTime loginTime = DateTime.Parse(split[2]);
            return new UserPrincipal(id, username, loginTime);
        }
    }
}
