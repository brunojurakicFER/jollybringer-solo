import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {toast} from 'react-toastify';
import Header from './Header.jsx';
import withAdminProtection from "./withAdminProtection.jsx";

const AdminDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    try {
      const response = await axios.get('/president-applications');
      console.log(response.data);
      setApplications(response.data);
    } catch (error) {
      toast.error('Failed to fetch applications');
    } finally {
      setIsLoading(false);
    }
  };

  const handleStatusUpdate = async (applicationId, status) => {
    try {
      await axios.patch(`/president-applications/${applicationId}`, {status});
      toast.success(`Application ${status.toLowerCase()}`);
      await fetchApplications(); // Refresh the list
    } catch (error) {
      toast.error('Failed to update application status');
    }
  };

  if (isLoading) return <div className="flex justify-center items-center h-full">Loading...</div>;

  return (<div className="h-dvh w-dvw">
    <Header/>
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6 text-white">President Role Applications</h1>

      {applications.length === 0 ? (<p className="text-gray-500">No pending applications</p>) : (
        <div className="bg-customGray rounded-lg shadow overflow-hidden">
          <table className="min-w-full divide-y divide-gray-200 text-white">
            <thead className="bg-customGray">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                User Name
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                User Email
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">
                Created At
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
            {applications.map((application) => (<tr key={application.id}>
              <td className="px-6 py-4 whitespace-nowrap text-sm">
                {application.userName}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm">
                {application.userEmail}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm">
                {new Date(application.createdAt).toLocaleDateString('hr')}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                      <span
                        className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${application.status === 'Pending' ? 'bg-yellow-100 text-yellow-800' : application.status === 'Approved' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}
                      >
                        {application.status}
                      </span>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                {application.status === 'Pending' && (<div className="flex gap-2">
                  <button
                    onClick={() => handleStatusUpdate(application.id, 'Approved')}
                    className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
                  >
                    Approve
                  </button>
                  <button
                    onClick={() => handleStatusUpdate(application.id, 'Rejected')}
                    className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
                  >
                    Reject
                  </button>
                </div>)}
              </td>
            </tr>))}
            </tbody>
          </table>
        </div>)}
    </div>
  </div>);
};

export default withAdminProtection(AdminDashboard);