using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using E_commerce.Data;
using E_commerce.Interfaces;
using E_commerce.Repositories;
using Microsoft.OpenApi.Models;
using Microsoft.AspNetCore.Diagnostics.HealthChecks;
using Microsoft.Extensions.Diagnostics.HealthChecks;
using System.Text.Json;
using E_commerce.Security;
using E_commerce.Service;
using System.IdentityModel.Tokens.Jwt;

using Microsoft.Extensions.Diagnostics.HealthChecks;


namespace E_commerce
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services)
        {

         /*   var heartbeatEndpoint = "http://localhost:44325/heartbeat"; // Replace with your actual endpoint URL
            services.AddHealthChecks()
                .AddCheck<HeartbeatHealthCheck>("heartbeat", failureStatus: HealthStatus.Unhealthy)
                .AddNpgSql(Configuration.GetConnectionString("MyConnectionString"))
                .AddDbContextCheck<YourDbContext>();
         */
            services.AddDbContext<YourDbContext>(options =>
         options.UseNpgsql(Configuration.GetConnectionString("MyConnectionString"),
             sqlServerOptions => sqlServerOptions.EnableRetryOnFailure())
         );

            services.AddScoped<IProductRepository, ProductRepository>();
            services.AddScoped<IUserRepository, UserRepository>();
            services.AddHealthChecks();
            services.AddScoped<UserPaymentRepository>();
            services.AddScoped<UserPaymentService>();
            services.AddScoped<UserInfoRepository>();
            services.AddScoped<UserInfoService>();

            services.AddControllers();

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "E-commerce API", Version = "v1" });
            });
            services.AddScoped<CustomAuthenticationProvider>();
            services.AddScoped<IUserService, YourUserServiceImplementation>();
            services.AddSingleton<JwtSecurityTokenHandler>();
            services.AddScoped<IAuthenticationProvider, CustomAuthenticationProvider>();


        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c =>
                {
                    c.SwaggerEndpoint("/swagger/v1/swagger.json", "E-commerce API v1");
                });
            }
            else
            {
                app.UseExceptionHandler("/error");
                app.UseHsts();
            }

            app.UseHealthChecks("/health", new HealthCheckOptions
            {
                ResponseWriter = WriteHealthCheckResponse
            });

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        private static Task WriteHealthCheckResponse(HttpContext context, HealthReport result)
        {
            // Configure the response
            context.Response.ContentType = "application/json";

            // Prepare the health check result
            var response = new
            {
                Status = result.Status.ToString(),
                TotalChecks = result.Entries.Count,
                Duration = result.TotalDuration,
                Results = result.Entries
            };

            // Serialize the response to JSON
            var options = new JsonSerializerOptions { WriteIndented = true };
            var json = JsonSerializer.Serialize(response, options);

            // Write the JSON response to the HTTP response stream
            return context.Response.WriteAsync(json);
        }
    }
}

