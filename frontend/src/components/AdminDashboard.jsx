import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {toast} from 'react-toastify';
import Header from "./Header.jsx";

const AdminDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    try {
      // const response = await axios.get('/api/president-applications');
      // setApplications(response.data);
    } catch (error) {
      toast.error('Failed to fetch applications');
    } finally {
      setIsLoading(false);
    }
  };

  const handleStatusUpdate = async (applicationId, status) => {
    // try {
    //   await axios.delete(`/api/president-applications/${applicationId}`, {
    //     data: {status}
    //   });
    //   toast.success(`Application ${status.toLowerCase()}`);
    //   await fetchApplications(); // Refresh the list
    // } catch (error) {
    //   toast.error('Failed to update application status');
    // }
  };

  if (isLoading) return <div className="flex justify-center items-center h-full">Loading...</div>;

  return (
    <div className={'h-dvh w-dvw'}>
      <Header/>
      <div className="p-8">
        <h1 className="text-2xl font-bold mb-6 text-white">President Role Applications</h1>

        {applications.length === 0 ? (
          <p className="text-gray-500">No pending applications</p>
        ) : (
          <div className="bg-customGray rounded-lg shadow overflow-hidden">
            <table className="min-w-full divide-y divide-gray-200 text-white">
              <thead className="bg-customGray">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                  Applicant
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                  Current Role
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                  Applied At
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                  Status
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                  Actions
                </th>
              </tr>
              </thead>
              <tbody className="bg-customGray divide-y divide-gray-200">
              {applications.map((application) => (
                <tr key={application.id}>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div>
                        <div className="text-sm font-medium">
                          {application.user.name}
                        </div>
                        <div className="text-sm">
                          {application.user.email}
                        </div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm">
                    {application.user.memberships[0]?.role.name}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm">
                    {new Date(application.createdAt).toLocaleDateString('hr')}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                      ${application.status === 'PENDING' ? 'bg-yellow-100 text-yellow-800' :
                      application.status === 'APPROVED' ? 'bg-green-100 text-green-800' :
                        'bg-red-100 text-red-800'}`}>
                      {application.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    {application.status === 'PENDING' && (
                      <div className="flex gap-2">
                        <button
                          onClick={() => handleStatusUpdate(application.id, 'APPROVED')}
                          className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
                        >
                          Approve
                        </button>
                        <button
                          onClick={() => handleStatusUpdate(application.id, 'REJECTED')}
                          className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
                        >
                          Reject
                        </button>
                      </div>
                    )}
                  </td>
                </tr>
              ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;