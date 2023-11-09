using Microsoft.Extensions.Diagnostics.HealthChecks;

namespace E_commerce.Health
{
    public class HeartbeatHealthCheck : IHealthCheck
    {
        private readonly string _heartbeatEndpoint;
        private readonly HttpClient _httpClient;

        public HeartbeatHealthCheck(string heartbeatEndpoint)
        {
            _heartbeatEndpoint = heartbeatEndpoint;
            _httpClient = new HttpClient();
        }

        public HeartbeatHealthCheck(HealthCheckResult healthCheckResult)
        {
            HealthCheckResult = healthCheckResult;
        }

        public HealthCheckResult HealthCheckResult { get; }

        public async Task<HealthCheckResult> CheckHealthAsync(
            HealthCheckContext context,
            CancellationToken cancellationToken = default)
        {
            try
            {
                var response = await _httpClient.GetAsync(_heartbeatEndpoint, cancellationToken);
                response.EnsureSuccessStatusCode();
                // logger
                // Customize the logic based on the response content if needed

                return HealthCheckResult.Healthy();
            }
            catch (Exception ex)
            {
                return HealthCheckResult.Unhealthy(ex.Message);
            }
        }
    }
}
